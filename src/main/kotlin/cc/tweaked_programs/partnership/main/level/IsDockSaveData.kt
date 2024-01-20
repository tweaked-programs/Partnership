package cc.tweaked_programs.partnership.main.level

import net.minecraft.nbt.*
import net.minecraft.util.datafix.DataFixTypes
import net.minecraft.world.level.ChunkPos
import net.minecraft.world.level.saveddata.SavedData


class IsDockSaveData : SavedData() {

    var dockableChunks: MutableMap<Long, Int> = mutableMapOf()

    override fun save(tag: CompoundTag): CompoundTag {
        val chunksTag = ListTag()
        dockableChunks.forEach { (pos, value) ->
            chunksTag.add(CompoundTag().apply {
                putLong("pos", pos)
                putInt("value", value)
            })
        }
        tag.put(CHUNKS_KEY_TAG, chunksTag)

        return tag
    }

    fun get(pos: ChunkPos): Int = dockableChunks[pos.toLong()] ?: 0

    companion object {
        const val FILE_NAME = "partnershipDockableChunks"
        const val CHUNKS_KEY_TAG = "dockableChunks"

        val FACTORY = Factory(
            this::create,
            this::load,
            DataFixTypes.LEVEL
        )

        fun create() = IsDockSaveData()

        private fun load(tag: CompoundTag): IsDockSaveData {
            val data = this.create()

            tag.getList(CHUNKS_KEY_TAG, Tag.TAG_COMPOUND.toInt()).forEach { entryTag ->
                entryTag as CompoundTag

                val pos = entryTag.getLong("pos")
                val value = entryTag.getInt("value")

                data.dockableChunks[pos] = value
            }

            return data
        }
    }
}