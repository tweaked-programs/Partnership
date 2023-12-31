package cc.tweaked_programs.partnership.main.block.cannon

import cc.tweaked_programs.partnership.main.MOD_ID
import cc.tweaked_programs.partnership.main.entity.CannonballEntity
import cc.tweaked_programs.partnership.main.menu.MarineCannonMenu
import cc.tweaked_programs.partnership.main.registries.BlockEntityRegistries
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.chat.Component
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.util.Mth
import net.minecraft.util.Mth.PI
import net.minecraft.util.Mth.clamp
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.phys.Vec3

class MarineCannonBlockEntity(pos: BlockPos, val state: BlockState) : BlockEntity(BlockEntityRegistries.MARINE_CANNON, pos, state), ExtendedScreenHandlerFactory {

    private var xRot: Float = 0F
    private var yRot: Float = 0F

    private var timeLeft: Int = 0
    private var shooting: Boolean = false

    private var power: Int = 0
    private var loaded: Boolean = false

    override fun saveAdditional(compoundTag: CompoundTag) {
        compoundTag.putFloat("xRot", xRot)
        compoundTag.putFloat("yRot", yRot)

        compoundTag.putInt("timeLeft", timeLeft)
        compoundTag.putBoolean("shooting", shooting)

        compoundTag.putInt("power", power)
        compoundTag.putBoolean("loaded", loaded)

        super.saveAdditional(compoundTag)
    }

    override fun load(compoundTag: CompoundTag) {
        super.load(compoundTag)

        xRot = compoundTag.getFloat("xRot")
        yRot = compoundTag.getFloat("yRot")

        timeLeft = compoundTag.getInt("timeLeft")
        shooting = compoundTag.getBoolean("shooting")

        power = compoundTag.getInt("power")
        loaded = compoundTag.getBoolean("loaded")
    }

    @Environment(EnvType.CLIENT)
    fun rotate(x: Float, y: Float) {
        val (newXRot, newYRot) = clampRot(xRot + x, yRot + y)
        xRot = newXRot
        yRot = newYRot
    }

    fun setRotation(x: Float, y: Float) {
        val (newXRot, newYRot) = clampRot(x, y)
        xRot = newXRot
        yRot = newYRot
        setChanged()
    }

    private fun clampRot(xRot: Float, yRot: Float): Pair<Float, Float> {
        return Pair(
            clamp(xRot, -75F, 75F),
            clamp(yRot, -45F, 45F)
        )
    }

    override fun writeScreenOpeningData(player: ServerPlayer?, buf: FriendlyByteBuf) {
        buf.writeBlockPos(blockPos)
    }

    override fun createMenu(syncId: Int, inventory: Inventory, player: Player): AbstractContainerMenu
            = MarineCannonMenu(syncId, inventory, blockPos)


    // Not really used but whatever
    override fun getDisplayName(): Component = Component.literal("block.partnership.marine_cannon")

    override fun getUpdatePacket(): Packet<ClientGamePacketListener>? {
        return ClientboundBlockEntityDataPacket.create(this)
    }

    override fun getUpdateTag(): CompoundTag {
        return saveWithoutMetadata()
    }

    override fun setChanged() {
        super.setChanged()
        if (hasLevel() && !level!!.isClientSide)
            level!!.sendBlockUpdated(blockPos, blockState, blockState, Block.UPDATE_ALL)
    }

    fun loadOneGunpowder() { power += 1 }
    fun fill() { loaded = true }
    fun prepareShoot() { if (shooting) return; shooting = true; timeLeft = COUNTDOWN }
    fun countdown() { if (timeLeft > 0) timeLeft-- }

    fun shoot(level: Level) {
        val newXRot = xRot + when (blockState.getValue(BlockStateProperties.HORIZONTAL_FACING)) {
            Direction.NORTH -> 180F
            Direction.EAST -> 270F
            Direction.SOUTH -> 0F
            Direction.WEST -> 90F
            else -> 0F
        }

        val x = -Mth.sin(newXRot * MAGIKK) * Mth.cos(yRot * MAGIKK).toDouble()
        val y = -Mth.sin(yRot * MAGIKK).toDouble()
        val z = Mth.cos(newXRot * MAGIKK) * Mth.cos(yRot * MAGIKK).toDouble()

        val spawn = blockPos.center.add(Vec3(x, y, z))

        val entity = CannonballEntity(level, spawn.x, spawn.y, spawn.z)
        val actualPower = power * 0.5 + 0.2
        entity.setDeltaMovement(x * actualPower, y * actualPower, z * actualPower)
        level.addFreshEntity(entity)

        level.addParticle(ParticleTypes.SMOKE, blockPos.x.toDouble(), blockPos.y.toDouble(), blockPos.z.toDouble(), blockPos.x.toDouble(), blockPos.y.toDouble(), blockPos.z.toDouble())

        // Reset
        loaded = false
        power = 0
        shooting = false
    }

    fun getXRot() = xRot
    fun getYRot() = yRot
    fun getTimeLeft() = timeLeft
    fun isShooting() = shooting
    fun getPower() = power
    fun isLoaded() = loaded

    companion object {
        val SYNC_ROT_NETWORK_ID = ResourceLocation(MOD_ID, "marine_cannon_apply_rotation")
        const val MAX_POWER: Int = 3
        const val COUNTDOWN: Int = 20

        private const val MAGIKK: Float = (kotlin.math.PI.toFloat() / 180f)
        
        fun tick(level: Level, blockPos: BlockPos, state: BlockState, be: MarineCannonBlockEntity) {
            if (level.isClientSide)
                return

            if (be.isShooting()) {
                if (be.getTimeLeft() > 0)
                    be.countdown()
                else be.shoot(level)

                be.setChanged()
            }
        }
    }
}

private fun toRadians(degree: Float): Float = degree / 180.0F * PI
