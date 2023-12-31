package cc.tweaked_programs.partnership.client.mixin;

import cc.tweaked_programs.partnership.main.entity.Lifebuoy;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidModel.class)
abstract class HumanoidModelMixin<T extends LivingEntity> {

    @Shadow @Final public ModelPart leftArm;

    @Shadow @Final public ModelPart rightArm;
    @Shadow @Final public ModelPart rightLeg;
    @Shadow @Final public ModelPart leftLeg;

    @Inject(
            method = "poseLeftArm",
            at = @At("TAIL")
    )
    private void partnership$tPose$poseLeftArm(T livingEntity, CallbackInfo ci) {
        if (livingEntity.getControlledVehicle() instanceof Lifebuoy) {
            this.leftArm.xRot = -1.6f;
            this.leftArm.yRot = -0.5f;
            this.leftArm.zRot = 0.3f;
        }
    }

    @Inject(
            method = "poseRightArm",
            at = @At("TAIL")
    )
    private void partnership$tPose$poseRightArm(T livingEntity, CallbackInfo ci) {
        if (livingEntity.getControlledVehicle() instanceof Lifebuoy) {
            this.rightArm.xRot = -1.6f;
            this.rightArm.yRot = 0.5f;
            this.rightArm.zRot = -0.3f;
        }
    }

    @Inject(
            method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V",
            at = @At("TAIL")
    )
    public void partnership$tPoseLegs$setupAnim(T livingEntity, float f, float g, float h, float i, float j, CallbackInfo ci) {
        if (livingEntity.getControlledVehicle() instanceof Lifebuoy) {
            float speed = (float) livingEntity.getDeltaMovement().lengthSqr();

            this.rightLeg.xRot = -0.5f + speed;
            this.rightLeg.yRot = 0.0f;
            this.rightLeg.zRot = 0.1f;

            this.leftLeg.xRot = -0.5f + speed;
            this.leftLeg.yRot = 0.0f;
            this.leftLeg.zRot = -0.1f;

            AnimationUtils.bobModelPart(this.rightLeg, h, 1.0f);
            AnimationUtils.bobModelPart(this.leftLeg, h, -1.5f);
        }
    }
}
