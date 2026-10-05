package net.satisfy.candlelight.core.block.entity;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ColorParticleOption;
import net.satisfy.candlelight.core.block.TypewriterBlock;
import net.satisfy.foundation.registry.FoundationParticles;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.candlelight.core.registry.EntityTypeRegistry;
import org.jetbrains.annotations.Nullable;

public class TypewriterEntity extends BlockEntity {
    public static final String PAPER_KEY = "paper";
    ItemStack paper = ItemStack.EMPTY;
    private static final RandomSource RANDOM = RandomSource.create();
    private final long[] keyTimes = new long[KEY_COUNT];
    private long spaceTime;
    private long enterTime;
    private long lineFeedTime;
    private long shakeTime;
    private float carriageFrom;
    private float carriageTo;
    private long carriageTime;
    private long carriageDuration = ADVANCE_MS;
    private int mashColumn;

    public TypewriterEntity(BlockPos pos, BlockState state) {
        super(EntityTypeRegistry.TYPE_WRITER_BLOCK_ENTITY.get(), pos, state);
    }

    public int advanceMashColumn(int steps) {
        mashColumn += steps;
        if (mashColumn >= MASH_LINE_LENGTH) {
            mashColumn = 0;
            return -1;
        }
        return mashColumn;
    }

    public ItemStack getPaper() {
        return paper;
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.saveAdditional(compoundTag, provider);
        if (!paper.isEmpty()) {
            writePaper(compoundTag, paper, provider);
        }
    }

    @Override
    protected void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.loadAdditional(compoundTag, provider);
        paper = readPaper(compoundTag, provider);
    }

    public void writePaper(CompoundTag nbt, ItemStack stack, HolderLookup.Provider provider) {
        if (stack == null || stack.isEmpty()) return;
        CompoundTag tag = new CompoundTag();
        stack.save(provider, tag);
        nbt.put(PAPER_KEY, tag);
    }

    public ItemStack readPaper(CompoundTag nbt, HolderLookup.Provider provider) {
        if (nbt.contains(PAPER_KEY)) {
            CompoundTag tag = nbt.getCompound(PAPER_KEY);
            if (!tag.isEmpty()) {
                return ItemStack.parseOptional(provider, tag);
            }
        }
        return ItemStack.EMPTY;
    }

    @Nullable
    @SuppressWarnings("unused")
    public Packet<ClientGamePacketListener> toUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @SuppressWarnings("unused")
    public CompoundTag toInitialChunkDataNbt(HolderLookup.Provider provider) {
        return saveWithoutMetadata(provider);
    }

    public void addPaper(ItemStack itemStack) {
        paper = itemStack;
        setChanged();
    }

    public void removePaper() {
        paper = ItemStack.EMPTY;
        setChanged();
    }

    public static final int KEY_COUNT = 7;
    private static final long PRESS_MS = 140L;
    private static final long ADVANCE_MS = 90L;
    private static final long RETURN_MS = 380L;
    private static final long SHAKE_MS = 120L;

    public void triggerSpace() {
        long now = Util.getMillis();
        this.spaceTime = now;
        this.shakeTime = now;
    }

    public void triggerEnter() {
        long now = Util.getMillis();
        this.enterTime = now;
        this.lineFeedTime = now;
        this.shakeTime = now;
    }

    public void triggerKeyBounce() {
        triggerKey(RANDOM.nextInt(KEY_COUNT));
    }

    public void triggerKey(int index) {
        long now = Util.getMillis();
        this.keyTimes[Math.floorMod(index, KEY_COUNT)] = now;
        this.shakeTime = now;
        spawnInk();
    }

    private void spawnInk() {
        if (level == null || !level.isClientSide || RANDOM.nextFloat() > 0.18f) return;
        Direction facing = getBlockState().hasProperty(TypewriterBlock.FACING) ? getBlockState().getValue(TypewriterBlock.FACING) : Direction.NORTH;
        double x = worldPosition.getX() + 0.5 - facing.getStepX() * 0.05 + (RANDOM.nextDouble() - 0.5) * 0.2;
        double z = worldPosition.getZ() + 0.5 - facing.getStepZ() * 0.05 + (RANDOM.nextDouble() - 0.5) * 0.2;
        double y = worldPosition.getY() + 0.5;
        level.addParticle(ColorParticleOption.create(FoundationParticles.DYE_SPLASH.get(), INK_COLOR), x, y, z, (RANDOM.nextDouble() - 0.5) * 0.04, 0.03, (RANDOM.nextDouble() - 0.5) * 0.04);
    }

    private static final int INK_COLOR = 0xFF1B1A24;

    public void setLineProgress(float v) {
        float target = Math.max(0f, Math.min(1f, v));
        if (target == this.carriageTo) return;
        this.carriageFrom = getCarriage();
        this.carriageTo = target;
        this.carriageTime = Util.getMillis();
        this.carriageDuration = target < this.carriageFrom ? RETURN_MS : ADVANCE_MS;
    }

    public float getLineProgress() {
        return carriageTo;
    }

    public void snapRoller() {
        this.lineFeedTime = Util.getMillis();
        setLineProgress(0f);
    }

    public float getCarriage() {
        float t = Math.min(1f, (Util.getMillis() - carriageTime) / (float) carriageDuration);
        float eased = carriageDuration == RETURN_MS ? 1f - (float) Math.pow(1f - t, 3) : t * (2f - t);
        float pos = carriageFrom + (carriageTo - carriageFrom) * eased;
        if (carriageDuration == RETURN_MS && t > 0.85f) {
            pos -= (float) Math.sin((t - 0.85f) / 0.15f * Math.PI) * 0.02f;
        }
        return pos;
    }

    public float getKeyPress(int index) {
        return pressCurve(keyTimes[index]);
    }

    public float getSpacePress() {
        return pressCurve(spaceTime);
    }

    public float getEnterPress() {
        return pressCurve(enterTime);
    }

    public float getLineFeed() {
        long dt = Util.getMillis() - lineFeedTime;
        if (dt < 0 || dt > RETURN_MS) return 0f;
        return (float) Math.sin(dt / (float) RETURN_MS * Math.PI);
    }

    public float getShake() {
        long dt = Util.getMillis() - shakeTime;
        if (dt < 0 || dt > SHAKE_MS) return 0f;
        float t = dt / (float) SHAKE_MS;
        return (float) Math.sin(t * Math.PI * 3) * (1f - t);
    }

    private static float pressCurve(long time) {
        long dt = Util.getMillis() - time;
        if (dt < 0 || dt > PRESS_MS) return 0f;
        float t = dt / (float) PRESS_MS;
        if (t < 0.25f) return t / 0.25f;
        float r = (t - 0.25f) / 0.75f;
        return (1f - r) * (1f - r) * (1f + 0.6f * (float) Math.sin(r * Math.PI));
    }

    @Override
    public boolean triggerEvent(int id, int param) {
        if (id >= EVENT_MASH && id <= EVENT_MASH_RETURN) {
            if (level != null && level.isClientSide) {
                for (int i = 0; i < KEY_COUNT; i++) {
                    if ((param & (1 << i)) != 0) triggerKey(i);
                }
                if ((param & 0x80) != 0) triggerSpace();
                if (id == EVENT_MASH_RETURN) {
                    triggerEnter();
                    snapRoller();
                } else {
                    setLineProgress((id - EVENT_MASH) / (float) MASH_LINE_LENGTH);
                }
            }
            return true;
        }
        return super.triggerEvent(id, param);
    }

    public static final int EVENT_MASH = 1;
    public static final int MASH_LINE_LENGTH = 24;
    public static final int EVENT_MASH_RETURN = EVENT_MASH + MASH_LINE_LENGTH;
}
