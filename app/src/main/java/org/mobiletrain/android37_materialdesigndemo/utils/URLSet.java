package org.mobiletrain.android37_materialdesigndemo.utils;

/**
 * 接口集合
 */
public class URLSet {

    /**
     * 头条新闻
     */
    public final static String URL_HEADLINE = "http://c.m.163.com/nc/article/headline/T1348647909107/0-40.html";

    /**
     * 糗事百科最新
     */
    public final static String URL_LATEST = "http://m2.qiushibaike.com/article/list/text?page=@&count=30&readarticles=[115645179]&rqcnt=24&r=33ddf4311458656188659";
    /**
     * 糗事百科详情
     */
    public final static String URL_LATEST_DETAIL = "http://m2.qiushibaike.com/article/@?rqcnt=59&r=33ddf4311458705349284";
    /**
     * 糗事百科评论
     */
    public final static String URL_LATEST_COMMTENT = "http://m2.qiushibaike.com/article/@/comments?page=@p&count=50&rqcnt=58&r=33ddf4311458705043143";

    /**
     * 军事   page：页码  list：tab项
     */
    public final static String URL_JUNSHIAD = "http://api.wap.miercn.com/api/apps/index.php?controller=newslist&action=cms_news_list&page=@&list=3&plat=android&proct=mierapp&versioncode=6&user_id=0&device_uuid=fa8bd1d4892d37e4406b7ea4a6089c20&os_version=SM-N9008,4.4.2&app_version=2.3.2.5";

    /**
     * 军事评论  aid：军事的id
     */
    public final static String URL_JUNSHICOMMENT = "http://api.wap.miercn.com/api/2.0.3/feedback.php?act=get_list&aid=@&page=@p&plat=android&proct=mierapp&versioncode=20160202&apiCode=6";

    /**
     * 体育 p：页码
     */
    public final static String URL_SPORT = "http://api.sina.cn/sinago/list.json?uid=4080a38f666ad14b&loading_ad_timestamp=0&platfrom_version=4.4.2&wm=b207&oldchwm=12040_0003&imei=352284042662407&from=6049495012&connection_type=2&chwm=12040_0003&AndroidID=6572e89d2eadcde2d08a32277763938b&v=1&s=20&IMEI=fa8bd1d4892d37e4406b7ea4a6089c20&p=@&MAC=3c723febd7e059a8ad6ea24687124ea5&channel=news_sports";

    /**
     * 体育评论  cmnt_id：体育中的Comment
     */
    public final static String URL_SPORTCOMMENT = "http://api.sina.cn/sinago/comment.json?uid=4080a38f666ad14b&hotcount=5&wm=b207&cmnt_id=comos-fxqnskh1070778_0_ty__fxqnskh1070778-comos-sports-cms&oldchwm=12040_0003&imei=352284042662407&action=list&from=6049495012&type=all&chwm=12040_0003";

    /**
     * 设计日报  下一页数据: http://news-at.zhihu.com/api/4/theme/4/before/7968334   before上一页的最后一条的数据id
     */
    public final static String URL_DESIGN = "http://news-at.zhihu.com/api/4/theme/4";

    /**
     * 设计日报详情
     */
    public final static String URL_DESIGNINFO = "http://news-at.zhihu.com/api/4/news/";



    /**
     * 图片列表
     */
    public final static String URL_PICTRUE = "http://iphone.myzaker.com/zaker/blog2news.php?app_id=12207&since_date=1458787686&nt=1&_appid=androidphone";

    /**
     * 图片评论
     */
    public final static String URL_IMAGE_CONTENT="http://c.myzaker.com/weibo/api_comment_article_url.php?_appid=AndroidPhone&_dev=31&_v=6.5&_version=6.52&act=get_comments&pk=?";



}


