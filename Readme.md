# City Info Parser

用于解析和风天气 api 提供的[城市信息 json 文件](https://cdn.heweather.com/china-city-list.json)，并且把
它储存到 Sqlite 数据库中。

## 解析方案
原有的 json 文件包含多条城市信息，每条信息中需要保存的有：
- 省中文名
- 市中文名
- 区或县中文名
- 地区对应的查询代码
同时，为了建立树形查询结构，要把省、市、区县的信息分别存入 3 个数据表中。

每一条城市信息的格式如下例子所示：
```json
{
  "id": "CN101110101",
  "cityEn": "xian",
  "cityZh": "西安",
  "countryCode": "CN",
  "countryEn": "China",
  "countryZh": "中国",
  "provinceEn": "shan-xi",
  "provinceZh": "陕西",
  "leaderEn": "xian",
  "leaderZh": "西安",
  "lat": "34.263161",
  "lon": "108.948024"
}
```
其中，cityZh 为市或区县的中文名，区别的方法是市的 cityZh 内容和 leaderZh 内容相等，或者和 provinceZh 相等。
> 大部分市的 leaderZh 内容为 cityZh，即自己的名称，但也有部分为 provinceZh，原因不明，无明显规律。

另外，json 数据中 id 的内容就是查询天气时所用的地区代号，其中所有数据的前 5 位内容都是 CN101，第 6、7 位为省的识别码，8、9
 位为每个省中的市的识别码，最后两位为每个市中的区县的识别码。
 这样，在全国的城市数据中，6、7 位就可唯一标识一个省，6 到 9 位可唯一标识一个市，6 到 11 位可唯一标识一个区县，即可
 作为各自数据表的主键。
 > 这个 api 的数据格式并不完全统一，各级别标识符的取值情况比较混乱（时而从 0 开始，时而从 1 开始），仅仅
能保证和不同数据的一一对应（即可以作数据表主键使用），分辨市和区县时不要通过 id 的格式进行判断。
 为了之后使用方便才用整数作数据表的主键，实际也可以使用 id 项的内容作为市和区县的主键，但是省表的
主键仍要自己处理。

## 重要依赖说明
- 使用 [SqlDelight](https://github.com/square/sqldelight) 来从 SQL 语句生成模型类
- 使用 [Android Debug Database](https://github.com/amitshekhariitbhu/Android-Debug-Database) 来简化 Android 平台数据库内容查询

## 注意
- 这个 api 的数据格式设计较混乱，不适用于其他类似情况的处理
- 仅仅适用于处理项目 app/src/main/assets 目录下的 json 文件，如果有 api 给出的数据发生变化则最好不要使用本项目代码进行解析
- 不要对 json 文件进行修改，尤其是更改数据条目的顺序

## Todo
城市信息添加成功后，显示全国省、市、区县分级列表