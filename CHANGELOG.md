# Change Log

- feat: 新功能
- fix: 错误修复
- perf: 性能改进 (不影响代码行为的前提下)
- refactor: 代码重构
- breaking change: 破坏性变更

## v6.6.8

### 新功能

- 支持用 autoxscript://sdcard/脚本/file.js 方式调用脚本

### 错误修复

- record root scripts error from bad shell output parsing

## v6.6.7

### 错误修复

- dex 加载问题

## v6.6.6

### 错误修复

- APP处于后台时Toast不显示的问题

## v6.6.5

### 错误修复

- 修复截图失败
- 修复前台服务打开闪退
- Android 11以上外部存储权限适配
- 新建文件 bug
- 修复存储权限判断逻辑
- 读取包、应用列表问题
- 截图失败问题
- 设置帧率问题 

## v6.6.4

### 错误修复

- wifi 连接 VSCode 报错
- 修复 Android 14 中工程项目显示异常问题
- 修复定时任务初始化错误

## v6.6.3

### 错误修复

- fix register receiver error when launching on android 14
- 广播接收问题
- 跳转通知权限页面报错
- 设置修改脚本路径不刷新
- 修复 delete 报错

## v6.6.2

跳过该版本的同步, 提交历史太混乱了

## v6.6.1

### 错误修复

- 修复文件名是数字的问题  
- 拦截删除代码时可能发生的崩溃

## v6.6.0

### 错误修复

- 兼容小米权限设置 (小米手机使用非小米系统判断后台弹出界面权限的问题)

## v6.5.9

### 新功能

- 支持 MQTT

### 错误修复

- 修复 dialogs build 报错

## v6.5.8 - new repo

- 一些迁移和清理工作, 使其在新仓库正常打包
- 旧的 v6.5.8 变更到 [CHANGELOG-old.md](CHANGELOG-old.md) 中查看

### 重构

- 优化 Splash 与 Drawer 界面, 更清爽
- 将打包选项中 "显示启动界面" 的默认值改为关闭

### 破坏性变更
- 移除 "新编辑器", 减少打包体积