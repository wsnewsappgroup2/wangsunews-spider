import pandas as pd
import datetime
import time
import pymysql
class ContentBasedRecommender():
    #  初始化参数
    def __init__(self):
        #  为用户推荐10部电影
        self.n_sim_news = 10
        self.n_rec_news = 10
        #  新闻数据
        self.news = 0
        self.ti = datetime.datetime.now()
        #  评分权重计算参数
        self.timeup = 864000
        self.g = 1.8

    def news_metadata(self,news_data):
        self.news = news_data
        self.news['last_time'] = self.news.apply(self.cal_lasttime,axis=1)
        self.news = self.news[self.news['last_time']<=self.timeup]
        self.news['r_weight'] = self.news.apply(self.r_weight,axis=1)
        self.news.to_csv('weight_news.csv',index=False)

    def recommend(self, u_csv,titletype):
        # u_csv = pd.read_csv(u_csv)
        # u_csv.columns = ['id', 'label', 'source', 'title', 'content', 'create_date', 'news_date']
        weight_news = pd.read_csv('weight_news.csv')
        weight_news = weight_news.sort_values('r_weight', ascending=False)
        weight_id = list(weight_news['id'])
        ## 抽出热度最高的未阅读的N个数据
        # watched = [int(i) for i in u_csv[id]]
        # un_watched = list(set(weight_news['id']) - set(watched))
        rec_news = weight_id[:self.n_rec_news]
        return rec_news

    def cal_lasttime(self,data):
        t_f = data['news_date']
        t_f = time.mktime(t_f.timetuple())
        t_i = time.mktime(self.ti.timetuple())
        last_time = t_i-t_f
        return last_time

    def r_weight(self,m_metadata):
        lasttime = m_metadata['last_time']
        last_hour = lasttime/3600
        page_view = m_metadata['source_comment_num']
        weigh = (page_view-1)/(pow(last_hour,self.g))
        return weigh

def most_recommend(u_id,title_type):
    engine = ContentBasedRecommender()
    rec = engine.recommend(u_id,title_type)
    print rec
    return rec

def update():
    news_content = getdata_mysql()
    engine = ContentBasedRecommender()
    engine.news_metadata(news_content)

def getdata_mysql():
    dbconn = pymysql.connect(
        host="10.8.229.94",
        database="wsnews",
        user="appTeam2",
        password="bZ^0!qyOu9&WJ*qO",
        port=63751,
        charset='utf8'
    )
    sqlcmd = "select * from news_list"
    news_list = pd.read_sql(sqlcmd, dbconn)
    news_list = news_list.drop('main_image',axis=1)
    sqlcmd2 = "select * from content_detail"
    news_content = pd.read_sql(sqlcmd2, dbconn)
    news_content = news_content.drop(['id','content_type','index_id'],axis=1)
    news_content = news_content.rename(columns={'news_id':'id'})
    news_merge = pd.merge(news_list, news_content, how='left', on='id')
    return news_merge

prediction=most_recommend(sys.argv[1]，sys.argv[2])
print (prediction)
# u_id = 'use.csv'
# update()
# list1 = most_recommend(u_id,title_type=1)