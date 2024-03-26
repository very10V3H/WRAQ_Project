package com.Very.very.Render.Hud;

import com.mojang.math.MatrixUtil;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class Guide {
    public static Vec3 Linear1 (Player player, Vec3 DestinationPos) {
        Vec3 vec3 = player.getEyePosition();
        float Scale = 1;
        HitResult hitResult = player.pick(1, 0, true);
        Vec3 vertical = hitResult.getLocation().subtract(vec3);
        double VerticalVecX = vertical.x, VerticalVecY = vertical.y, VerticalVecZ = vertical.z;

        Vec3 pos = vec3.add(vertical).scale(Scale);
        double x0 = pos.x, y0 = pos.y, z0 = pos.z;

        float Distance = 0;
        pos.add(VerticalVecX * Distance, VerticalVecY * Distance, VerticalVecZ * Distance);
        Vec3 S = pos.subtract(DestinationPos);

        double a = VerticalVecX, b = VerticalVecY, c = VerticalVecZ;
        double x1 = pos.x, y1 = pos.y, z1 = pos.z;
        double m = S.x, n = S.y, p = S.z;
        double X = -(-b * n * x0 - c * p * x0 - a * m * x1 + b * m * y0 - b * m * y1 + c * m * z0 - c * m * z1) / (a * m + b * n + c * p);
        double Y = -(a * n * x0 - a * n * x1 - a * m * y0 - c * p * y0 - b * n * y1 + c * n * z0 - c * n * z1) / (a * m + b * n + c * p);
        double Z = -(a * p * x0 - a * p * x1 + b * p * y0 - b * p * y1 - a * m * z0 - b * n * z0 - c * p * z1) / (a * m + b * n + c * p);
        return new Vec3(X,Y,Z);

    }
}
