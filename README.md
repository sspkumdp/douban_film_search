# douban_film_search
仅用作学习   
豆瓣电影爬虫数据+lucene的搜索引擎。springmvc+spring+mybatis框架   

构建数据库的sql文件在sql文件夹下   

基本流程：   
1）使用doubanfilmspider项目爬取豆瓣电影数据，存入数据库   
2）quartz定时任务获取数据库中未存入lucene中的记录，转为Document并建立索引   
3）对电影使用关键词查询时，按电影名+别名+简介+导演+演员各一定权重查询。   