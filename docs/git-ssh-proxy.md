# Git 与 SSH 代理配置说明

检查日期：2026-10-21。本文根据本次会话中的只读检查整理，区分配置事实、实际运行状态和待验证推断。

## 1. 当前仓库的连接方式

仓库目录：`E:\work\BehaviourTree`

远程地址：

```text
git@github.com:QingTian001/BehaviourTree.git
```

当前仓库使用 SSH 连接 GitHub。SSH 代理由 SSH 配置中的 `ProxyCommand` 决定。

## 2. SSH 代理配置的位置

配置文件：`C:\Users\Administrator\.ssh\config`，第 1—3 行：

```sshconfig
Host github.com
    User git
    ProxyCommand connect -S 127.0.0.1:1080 %h %p
```

含义：

- `Host github.com`：配置匹配 GitHub 主机。
- `User git`：使用 GitHub 的 SSH 用户 `git`。
- `ProxyCommand`：通过 `connect` 代理助手建立连接。
- `-S 127.0.0.1:1080`：使用本机 1080 端口的 SOCKS 代理。
- `%h` 和 `%p`：由 SSH 替换为目标主机和端口；本次解析结果是 `github.com:22`。

当前配置的连接链路：

```text
GitHub SSH 请求 → connect.exe → 本机 Shadowsocks（127.0.0.1:1080）→ github.com:22
```

已确认的代理助手路径：

```text
C:\Program Files\Git\mingw64\bin\connect.exe
```

只读检查确认：1080 端口存在监听，所属进程为 `Shadowsocks`。这证明本地代理程序正在监听，不等同于已验证 GitHub SSH 登录或推送成功。

## 3. 全局 Git 中的另一项代理设置

配置来源：`C:\Users\Administrator\.gitconfig`。

```ini
[https]
    proxy = 127.0.0.1:1080
```

Git 查询返回的键为 `https.proxy`。本文只记录该配置项确实存在，未验证当前 Git 版本的 HTTPS 传输是否实际采用它。

当前仓库使用 SSH，判断其代理路径应查看 `ProxyCommand`，不能只依据这项全局设置。

## 4. 本机存在两套 SSH

| SSH 实现 | 可执行文件 |
| --- | --- |
| Windows OpenSSH | `C:\Windows\System32\OpenSSH\ssh.exe` |
| Git for Windows 自带 SSH | `C:\Program Files\Git\usr\bin\ssh.exe` |

普通命令查找中，Windows OpenSSH 排在前面。对 Git 自带 SSH 执行 `-vv -G github.com` 时，已确认它读取上述用户配置，并得到：

```text
hostname github.com
port 22
proxycommand connect -S 127.0.0.1:1080 %h %p
```

`-G` 只打印解析后的配置，不建立 SSH 连接。排查时应区分终端中的 `ssh` 和 Git 实际调用的 SSH 实现。

## 5. 此前连接失败的线索

远程只读查询曾出现以下错误：

```text
exec connect -S 127.0.0.1:1080 github.com 22
exec : The term 'exec' is not recognized ...
kex_exchange_identification: Connection closed by remote host
```

已确认的环境变量状态：

```text
SHELL(Process)=C:\Windows\System32\WindowsPowerShell\v1.0\powershell.exe
SHELL(User)=空
SHELL(Machine)=空
```

SSH 配置文件本身写的是 `connect`，没有 `exec`。错误日志中的 `exec` 出现在后续执行链中，而 PowerShell 不识别该命令。

据此推断：当前进程的 Shell 设置与 SSH 代理执行方式存在兼容问题。尚未通过切换 Shell 的对照测试确认全部原因，也未追溯该进程变量由哪个启动环节设置，不能据此认定 Shadowsocks 或代理地址有问题。

## 6. 可复用的只读检查命令

以下命令用于 PowerShell，不修改配置，也不执行推送。

查看仓库远程地址及 Git 代理配置来源：

```powershell
git -C E:\work\BehaviourTree remote -v
git -C E:\work\BehaviourTree config --show-origin --show-scope --get-regexp '(^core\.sshcommand$|^ssh\.variant$|^http\..*proxy$|^http\.proxy$|^https\.proxy$)'
```

检查 SSH 配置中的代理项，以及 Git 自带 SSH 的最终解析结果：

```powershell
Select-String -LiteralPath 'C:\Users\Administrator\.ssh\config' -Pattern '^\s*(Host|User|HostName|Port|Include|Match|ProxyCommand|ProxyJump)\b'
& 'C:\Program Files\Git\usr\bin\ssh.exe' -vv -G github.com
```

检查本地代理监听及 Shell 变量来源：

```powershell
Get-NetTCPConnection -LocalPort 1080 -State Listen
[Environment]::GetEnvironmentVariable('SHELL', 'Process')
[Environment]::GetEnvironmentVariable('SHELL', 'User')
[Environment]::GetEnvironmentVariable('SHELL', 'Machine')
```

本次排查未修改 Git、SSH、代理程序或持久环境变量配置。后续如需修复连接，应先确认 Git 实际调用的 SSH 和 Shell，再用仅对单次命令生效的设置进行对照验证。
