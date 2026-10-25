# 精通界面圆形图集

`src/main/resources/assets/vmd/textures/gui/mastery/circles.png` 由本目录的 `GenMasteryAtlas.java` 生成，界面通过 `MasteryScreen.blitCell` 复用。

## 图集规格

- 1024 x 512，8 列 x 4 行，每格 128px，带透明通道
- 圆环/菱形边缘会预填一圈 alpha=1 的同色渗色；所有 alpha=0 的全透明像素也写入所属格子的颜色（alpha 仍为 0），柔光外圈铺同色 alpha=1 底。这样线性过滤/模糊时不会与透明黑混合产生黑边，同时不会把整格填成方形底色
- 生成器使用自定义 PNG 写入器，避免 ImageIO 把 alpha=0 的 RGB 清零
- `circles.png.mcmeta` 开启 `blur` / `clamp`，缩放时使用线性过滤，避免锯齿

| 位置 | 内容 |
|---|---|
| (0,0) | 浅色填充圆 |
| (1,0) | 蓝色悬停柔光 |
| (2,0) | 蓝色悬停环 |
| (3,0) | 锁定环（浅灰） |
| (4,0) | 解锁环（深灰蓝） |
| 行 1，列 0-4 | 精密/主宰/巫术/坚决/启迪 的柔光 |
| 行 2，列 0-4 | 五条路径的圆环 |
| 行 3，列 0-4 | 五条路径的基石菱形 |

## 重新生成

```powershell
javac -encoding UTF-8 -d build/texture-gen/classes tools/mastery_texture/GenMasteryAtlas.java
java -cp build/texture-gen/classes GenMasteryAtlas "src/main/resources/assets/vmd/textures/gui/mastery/circles.png"
```

颜色需与 `fun.wraq.process.system.mastery.MasteryTree` 保持一致；图集只负责圆形外观，点数文字仍由界面绘制。
