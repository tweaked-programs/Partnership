package cc.tweaked_programs.partnership.main.block.boatyard

import net.minecraft.world.phys.shapes.Shapes

object BoatyardShapes {

    val LEFT_NORTH = Shapes.empty()
        .let { Shapes.or(it, Shapes.create(-0.3125, 0.8125, 0.0625, 0.8125, 0.9375, 0.1875)) }
        .let { Shapes.or(it, Shapes.create(-0.3125, 0.8125, 0.8125, 0.8125, 0.9375, 0.9375)) }
        .let { Shapes.or(it, Shapes.create(-1.0, 0.9375, 0.0, 1.0, 1.0, 1.0)) }
        .let { Shapes.or(it, Shapes.create(-0.9375, 0.0, 0.0625, -0.8125, 0.9375, 0.1875)) }
        .let { Shapes.or(it, Shapes.create(-0.90625, 0.25, 0.1875, -0.84375, 0.3125, 0.8125)) }
        .let { Shapes.or(it, Shapes.create(-0.9375, 0.0, 0.8125, -0.8125, 0.9375, 0.9375)) }
        .let { Shapes.or(it, Shapes.create(-0.8125, 0.1875, 0.0625, -0.3125, 0.9375, 0.9375)) }
        .let { Shapes.or(it, Shapes.create(0.84375, 0.25, 0.1875, 0.90625, 0.3125, 0.8125)) }
        .let { Shapes.or(it, Shapes.create(0.8125, 0.0, 0.8125, 0.9375, 0.9375, 0.9375)) }
        .let { Shapes.or(it, Shapes.create(0.8125, 0.0, 0.0625, 0.9375, 0.9375, 0.1875)) }

    val LEFT_EAST = Shapes.empty()
        .let { Shapes.or(it, Shapes.create(0.8125, 0.8125, -0.3125, 0.9375, 0.9375, 0.8125)) }
        .let { Shapes.or(it, Shapes.create(0.0625, 0.8125, -0.3125, 0.1875, 0.9375, 0.8125)) }
        .let { Shapes.or(it, Shapes.create(0.0, 0.9375, -1.0, 1.0, 1.0, 1.0)) }
        .let { Shapes.or(it, Shapes.create(0.8125, 0.0, -0.9375, 0.9375, 0.9375, -0.8125)) }
        .let { Shapes.or(it, Shapes.create(0.1875, 0.25, -0.90625, 0.8125, 0.3125, -0.84375)) }
        .let { Shapes.or(it, Shapes.create(0.0625, 0.0, -0.9375, 0.1875, 0.9375, -0.8125)) }
        .let { Shapes.or(it, Shapes.create(0.0625, 0.1875, -0.8125, 0.9375, 0.9375, -0.3125)) }
        .let { Shapes.or(it, Shapes.create(0.1875, 0.25, 0.84375, 0.8125, 0.3125, 0.90625)) }
        .let { Shapes.or(it, Shapes.create(0.0625, 0.0, 0.8125, 0.1875, 0.9375, 0.9375)) }
        .let { Shapes.or(it, Shapes.create(0.8125, 0.0, 0.8125, 0.9375, 0.9375, 0.9375)) }

    val LEFT_SOUTH = Shapes.empty()
        .let { Shapes.or(it, Shapes.create(0.1875, 0.8125, 0.8125, 1.3125, 0.9375, 0.9375)) }
        .let { Shapes.or(it, Shapes.create(0.1875, 0.8125, 0.0625, 1.3125, 0.9375, 0.1875)) }
        .let { Shapes.or(it, Shapes.create(0.0, 0.9375, 0.0, 2.0, 1.0, 1.0)) }
        .let { Shapes.or(it, Shapes.create(1.8125, 0.0, 0.8125, 1.9375, 0.9375, 0.9375)) }
        .let { Shapes.or(it, Shapes.create(1.84375, 0.25, 0.1875, 1.90625, 0.3125, 0.8125)) }
        .let { Shapes.or(it, Shapes.create(1.8125, 0.0, 0.0625, 1.9375, 0.9375, 0.1875)) }
        .let { Shapes.or(it, Shapes.create(1.3125, 0.1875, 0.0625, 1.8125, 0.9375, 0.9375)) }
        .let { Shapes.or(it, Shapes.create(0.09375, 0.25, 0.1875, 0.15625, 0.3125, 0.8125)) }
        .let { Shapes.or(it, Shapes.create(0.0625, 0.0, 0.0625, 0.1875, 0.9375, 0.1875)) }
        .let { Shapes.or(it, Shapes.create(0.0625, 0.0, 0.8125, 0.1875, 0.9375, 0.9375)) }

    val LEFT_WEST = Shapes.empty()
        .let { Shapes.or(it, Shapes.create(0.0625, 0.8125, 0.1875, 0.1875, 0.9375, 1.3125)) }
        .let { Shapes.or(it, Shapes.create(0.8125, 0.8125, 0.1875, 0.9375, 0.9375, 1.3125)) }
        .let { Shapes.or(it, Shapes.create(0.0, 0.9375, 0.0, 1.0, 1.0, 2.0)) }
        .let { Shapes.or(it, Shapes.create(0.0625, 0.0, 1.8125, 0.1875, 0.9375, 1.9375)) }
        .let { Shapes.or(it, Shapes.create(0.1875, 0.25, 1.84375, 0.8125, 0.3125, 1.90625)) }
        .let { Shapes.or(it, Shapes.create(0.8125, 0.0, 1.8125, 0.9375, 0.9375, 1.9375)) }
        .let { Shapes.or(it, Shapes.create(0.0625, 0.1875, 1.3125, 0.9375, 0.9375, 1.8125)) }
        .let { Shapes.or(it, Shapes.create(0.1875, 0.25, 0.09375, 0.8125, 0.3125, 0.15625)) }
        .let { Shapes.or(it, Shapes.create(0.8125, 0.0, 0.0625, 0.9375, 0.9375, 0.1875)) }
        .let { Shapes.or(it, Shapes.create(0.0625, 0.0, 0.0625, 0.1875, 0.9375, 0.1875)) }

    val RIGHT_NORTH = Shapes.empty()
        .let { Shapes.or(it, Shapes.create(0.6875, 0.8125, 0.0625, 1.8125, 0.9375, 0.1875)) }
        .let { Shapes.or(it, Shapes.create(0.6875, 0.8125, 0.8125, 1.8125, 0.9375, 0.9375)) }
        .let { Shapes.or(it, Shapes.create(0.0, 0.9375, 0.0, 2.0, 1.0, 1.0)) }
        .let { Shapes.or(it, Shapes.create(0.0625, 0.0, 0.0625, 0.1875, 0.9375, 0.1875)) }
        .let { Shapes.or(it, Shapes.create(0.09375, 0.25, 0.1875, 0.15625, 0.3125, 0.8125)) }
        .let { Shapes.or(it, Shapes.create(0.0625, 0.0, 0.8125, 0.1875, 0.9375, 0.9375)) }
        .let { Shapes.or(it, Shapes.create(0.1875, 0.1875, 0.0625, 0.6875, 0.9375, 0.9375)) }
        .let { Shapes.or(it, Shapes.create(1.84375, 0.25, 0.1875, 1.90625, 0.3125, 0.8125)) }
        .let { Shapes.or(it, Shapes.create(1.8125, 0.0, 0.8125, 1.9375, 0.9375, 0.9375)) }
        .let { Shapes.or(it, Shapes.create(1.8125, 0.0, 0.0625, 1.9375, 0.9375, 0.1875)) }

    val RIGHT_EAST = Shapes.empty()
        .let { Shapes.or(it, Shapes.create(0.8125, 0.8125, 0.6875, 0.9375, 0.9375, 1.8125)) }
        .let { Shapes.or(it, Shapes.create(0.0625, 0.8125, 0.6875, 0.1875, 0.9375, 1.8125)) }
        .let { Shapes.or(it, Shapes.create(0.0, 0.9375, 0.0, 1.0, 1.0, 2.0)) }
        .let { Shapes.or(it, Shapes.create(0.8125, 0.0, 0.0625, 0.9375, 0.9375, 0.1875)) }
        .let { Shapes.or(it, Shapes.create(0.1875, 0.25, 0.09375, 0.8125, 0.3125, 0.15625)) }
        .let { Shapes.or(it, Shapes.create(0.0625, 0.0, 0.0625, 0.1875, 0.9375, 0.1875)) }
        .let { Shapes.or(it, Shapes.create(0.0625, 0.1875, 0.1875, 0.9375, 0.9375, 0.6875)) }
        .let { Shapes.or(it, Shapes.create(0.1875, 0.25, 1.84375, 0.8125, 0.3125, 1.90625)) }
        .let { Shapes.or(it, Shapes.create(0.0625, 0.0, 1.8125, 0.1875, 0.9375, 1.9375)) }
        .let { Shapes.or(it, Shapes.create(0.8125, 0.0, 1.8125, 0.9375, 0.9375, 1.9375)) }

    val RIGHT_SOUTH = Shapes.empty()
        .let { Shapes.or(it, Shapes.create(-0.8125, 0.8125, 0.8125, 0.3125, 0.9375, 0.9375)) }
        .let { Shapes.or(it, Shapes.create(-0.8125, 0.8125, 0.0625, 0.3125, 0.9375, 0.1875)) }
        .let { Shapes.or(it, Shapes.create(-1.0, 0.9375, 0.0, 1.0, 1.0, 1.0)) }
        .let { Shapes.or(it, Shapes.create(0.8125, 0.0, 0.8125, 0.9375, 0.9375, 0.9375)) }
        .let { Shapes.or(it, Shapes.create(0.84375, 0.25, 0.1875, 0.90625, 0.3125, 0.8125)) }
        .let { Shapes.or(it, Shapes.create(0.8125, 0.0, 0.0625, 0.9375, 0.9375, 0.1875)) }
        .let { Shapes.or(it, Shapes.create(0.3125, 0.1875, 0.0625, 0.8125, 0.9375, 0.9375)) }
        .let { Shapes.or(it, Shapes.create(-0.90625, 0.25, 0.1875, -0.84375, 0.3125, 0.8125)) }
        .let { Shapes.or(it, Shapes.create(-0.9375, 0.0, 0.0625, -0.8125, 0.9375, 0.1875)) }
        .let { Shapes.or(it, Shapes.create(-0.9375, 0.0, 0.8125, -0.8125, 0.9375, 0.9375)) }

    val RIGHT_WEST = Shapes.empty()
        .let { Shapes.or(it, Shapes.create(0.0625, 0.8125, -0.8125, 0.1875, 0.9375, 0.3125)) }
        .let { Shapes.or(it, Shapes.create(0.8125, 0.8125, -0.8125, 0.9375, 0.9375, 0.3125)) }
        .let { Shapes.or(it, Shapes.create(0.0, 0.9375, -1.0, 1.0, 1.0, 1.0)) }
        .let { Shapes.or(it, Shapes.create(0.0625, 0.0, 0.8125, 0.1875, 0.9375, 0.9375)) }
        .let { Shapes.or(it, Shapes.create(0.1875, 0.25, 0.84375, 0.8125, 0.3125, 0.90625)) }
        .let { Shapes.or(it, Shapes.create(0.8125, 0.0, 0.8125, 0.9375, 0.9375, 0.9375)) }
        .let { Shapes.or(it, Shapes.create(0.0625, 0.1875, 0.3125, 0.9375, 0.9375, 0.8125)) }
        .let { Shapes.or(it, Shapes.create(0.1875, 0.25, -0.90625, 0.8125, 0.3125, -0.84375)) }
        .let { Shapes.or(it, Shapes.create(0.8125, 0.0, -0.9375, 0.9375, 0.9375, -0.8125)) }
        .let { Shapes.or(it, Shapes.create(0.0625, 0.0, -0.9375, 0.1875, 0.9375, -0.8125)) }
}