# solar-rpg-editor
太阳rpg编辑器相关开源库

[太阳rpg编辑器官网 www.heduim.com](http://www.heduim.com)



<a href="https://www.bilibili.com/video/BV1BV411H7Rw" target="_blank">太阳rpg编辑器视频宣传片</a>

<a href="https://jq.qq.com/?_wv=1027&k=db0vJPbN" target="_blank">太阳rpg编辑器交流QQ群:941442872</a>

<a href="https://github.com/heduim-solar/solar-rpg-editor/wiki" target="_blank">太阳rpg编辑器wiki</a>

作者联系 QQ: 1053831109

可执行的exe程序请在此仓库releases里下载

编辑器推荐安装在默认路径(D:/太阳rpg编辑器) 

编辑器初次安装后需要在文件 设置里检查设置是否自动设置正确(比如魔兽根目录是否正确)

编辑器在运行中会依赖安装目录下的data目录 魔兽目录.如需使用快捷按钮打开vscode等程序需要配置对应程序的程序路径.


 
太阳rpg架构
<img src="https://raw.githubusercontent.com/heduim-solar/solar-rpg-editor/main/%E5%A4%AA%E9%98%B3rpg%E6%9E%B6%E6%9E%84.jpg"/>

编辑方式:（括号内为大约的使用时长占比）

TS/lua/j或与T混编作者： 太阳rpg编辑器(40%) + VSCode/WebStorm(40%) + WPS(10%) + WE(10%)

T触发作者：                    WE(60%) + 太阳rpg编辑器(30%) + WPS(10%)




编辑工具主要职能:

太阳rpg编辑器：   整合其他工具、xlsx物编、UI编辑、批量编辑预览、增量秒级保存、支持TS脚本...（此处省略1万字）等特别的编辑功能

WE：                    地形、T触发、等基础的编辑功能

VSCode/WS：      编辑TS/Lua/jass

WPS：                  物编 (太阳rpg编辑器打开地图后有xlsx表格格式的物编,单元格内容#等于空)



太阳rpg编辑器保存的是标准的w3x文件，所以太阳rpg编辑器可与任何其他编辑器同时使用，不会冲突！

（不要在编辑器里多选一，在不同的编辑需求时请选择合适的编辑器）


太阳rpg界面
<img src="https://raw.githubusercontent.com/heduim-solar/solar-rpg-editor/main/%E5%A4%AA%E9%98%B3rpg%E7%95%8C%E9%9D%A2.png"/>

太阳RPG编辑器优势

1.编辑器非侵入式.在获得太阳rpg的功能特性时依旧可以使用其他编辑器与工具

2.编辑器物编xlsx编辑 编辑响应快

3.编辑器打开文件后自动解压为dir文件夹 方便编辑地图内的所有文件

4.编辑器保存测试为增量更新 可秒级测试(只更新已修改的,WE为全量更新每次保存都卡)

5.编辑器提供强大的Pcg支持体系以及插件体系(pcg=程序内容生成)

6.编辑器提供了更多的功能,比如批量预览图标模型等等...

7.通过shift ctrl可多选模型或物编进行批量预览编辑

8.支持可视化UI构建器 所见即所得 生成的是基于React的tsx代码




<img src="https://github.com/heduim-solar/solar-rpg-editor/blob/main/%E9%A6%96%E9%A1%B5.png"/>

<img src="https://github.com/heduim-solar/solar-rpg-editor/raw/main/%E6%8F%92%E4%BB%B6%E7%AE%A1%E7%90%86.png"/>

<img src="https://github.com/heduim-solar/solar-rpg-editor/blob/main/%E6%A8%A1%E5%9E%8B%E6%89%B9%E9%87%8F%E9%A2%84%E8%A7%88.png"/>

<img src="https://github.com/heduim-solar/solar-rpg-editor/blob/main/%E6%A8%A1%E5%9E%8B%E7%BC%96%E8%BE%91.png"/>

<img src="https://github.com/heduim-solar/solar-rpg-editor/blob/main/%E6%A8%A1%E5%9E%8B%E9%A2%84%E8%A7%88.png"/>

<img src="https://github.com/heduim-solar/solar-rpg-editor/blob/main/%E5%9B%BE%E7%89%87%E6%89%B9%E9%87%8F%E9%A2%84%E8%A7%88.png"/>


