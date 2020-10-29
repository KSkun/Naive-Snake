# Naive-Snake

KSkun 的玩蛇工具

## 支持功能

管理 subg.client 进程，可视化手动玩蛇，看着算法玩蛇。

## 使用方法

将项目 clone 至本地后命令行执行 `gradle run -args="-m"` 即可。

上述指令中参数列表为 `-m` 手玩开关。如果想看算法玩，按照开发指南编写自己的 `AlgPlayer` 后，设置参数列表为 `-a 算法名称`，项目中已包含一个纯随机算法，可以用 `-a random` 演示。（注：`random` 死的特别快）

## 开发指南

在项目的 `src/main/java/moe/ksmeow/snake/alg` 下创建自己的算法类，继承 `AlgPlayerView`（只依赖视野决策）或 `AlgPlayerCheat`（依赖全图决策），实现 `play` 方法，再在 `AlgPlayers` 类中注册算法类即可。

项目中实现了一个纯随机算法的实例，可以作为实现参考。