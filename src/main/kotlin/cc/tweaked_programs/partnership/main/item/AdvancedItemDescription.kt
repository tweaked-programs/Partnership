package cc.tweaked_programs.partnership.main.item

import cc.tweaked_programs.partnership.main.BRAND_COLOR
import net.minecraft.network.chat.Component

object AdvancedItemDescription {

    private const val TOOLTIP_WIDTH: Int = 35

    fun appendHoverText(descriptionId: String, complexDescription: Boolean, list: MutableList<Component>) {
        val holdsShift = isHoldingShift()
        if (complexDescription) {
            val keyName = Component.translatable("key.keyboard.shift").string

            val info = "§8" + Component.translatable("tooltip.partnership.holdShiftForMore").string.format(
                "${if (holdsShift) "§f" else "§7"}${keyName}§8"
            )

            list.add(Component.literal(info))
        }

        if (holdsShift || !complexDescription) {
            val lines: List<String> = Component.translatable("tooltip.$descriptionId").string
                .split("\n".toRegex())
                .dropLastWhile { it.isEmpty() }
                .toList()

            lines.forEach { line ->
                val wrapped = line.split(' ').fold(mutableListOf<String>()) { result, word ->
                    val lastLine = result.lastOrNull()
                    if (lastLine != null && lastLine.length + word.length <= TOOLTIP_WIDTH)
                        result[result.lastIndex] = "$lastLine $word"
                    else result.add(word)

                    result
                }

                list.addAll(wrapped.map { Component.literal("§o$it§r").withColor(BRAND_COLOR) })
            }
        }
    }

    //TODO("Replace with ClientService class")
    private fun isHoldingShift(): Boolean {
        return false
    }
}