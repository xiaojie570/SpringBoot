### 删除远程仓库

$ git pull origin master          # 将远程仓库里面的项目拉下来<br>
$ dir                             # 查看有哪些文件夹<br>
$ git rm -r --cached target       # 删除target文件夹<br>
$ git commit -m '删除了target'    # 提交,添加操作说明<br>
$ git push -u origin master       # 将本次更改更新到github项目上去<br>

注意：本地项目中的target文件夹不受操作影响，删除的只是远程仓库中的target，可以放心删除