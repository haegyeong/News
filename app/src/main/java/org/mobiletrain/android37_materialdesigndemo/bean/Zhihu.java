package org.mobiletrain.android37_materialdesigndemo.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/3/22.
 */
public class Zhihu {


    /**
     * stories : [{"id":8032481,"title":"2016年\u201c世界最美的书\u201d获奖作品完整版公布","images":["http://pic1.zhimg.com/1f17f33f9d1cbf323018fbcec047d624.jpg"],"type":2},{"id":8032449,"title":"迪特-拉姆斯（Dieter Rams）博朗之魂\u2014工业设计之父","images":["http://pic1.zhimg.com/a6e3436b5fe00fbfd89cba1d964cd48c.jpg"],"type":2},{"id":8011190,"title":"Adobe 放大招，全面打通界面、交互、原型制作！","images":["http://pic4.zhimg.com/febb9fa5168f1612742e43aa95af8777.jpg"],"type":2},{"id":8020992,"title":"在寸土寸金的北京，这所学校竟然还有农田","images":["http://pic3.zhimg.com/7378d045b24f076cd75d04de4fb6274e.jpg"],"type":2},{"id":8013801,"title":"iOS 设计的这些神细节你注意到了吗？（超多图）","images":["http://pic4.zhimg.com/22d03b883e1dafb0d29a78fa620c74db.jpg"],"type":0},{"id":8008481,"title":"黑的无底洞","images":["https://pic2.zhimg.com/8db3ced87ef6626bc3882ecfc85628f1_b.png"],"type":0},{"id":8005590,"title":"谈到乔布斯的设计嗅觉，苹果首席设计师讲了一个故事","images":["http://pic1.zhimg.com/2a3ac1c6b3645e5a5ae3979e4a189100.jpg"],"type":2},{"id":8005027,"title":"虽然预算有限，我还是想要一个私人订制版的家","images":["http://pic4.zhimg.com/34957a0a2d0be3530b39afd1222fa1af.jpg"],"type":2},{"id":8000217,"title":"UX行业国际调研报告","images":["http://pic1.zhimg.com/e26d43e23921b3cce7a587c320d18c50.jpg"],"type":2},{"id":7986790,"title":"苹果 \u201c新 iPhone\u201d 发布会时间确认！这里有份最全的预测分析","images":["http://pic1.zhimg.com/58b012111f9b8e6e37451bb1c2a5a260.jpg"],"type":2},{"id":7984215,"title":"对话英国Horse设计工作室：农夫山泉玻璃瓶中的万物自然","images":["http://pic4.zhimg.com/ab136b56d9965c0499e7c4536b8f5b47.jpg"],"type":2},{"id":7984210,"title":"产品测试：只找五位用户就够了？为什么？","images":["http://pic1.zhimg.com/7f0d8ceabc660b81f37affd4159c5c6c.jpg"],"type":2},{"id":7982790,"title":"VR 来了，5 年后你可能在虚拟世界里做设计","images":["http://pic2.zhimg.com/b1f63e1b05e83473d9af75483533ad6d.jpg"],"type":0},{"id":7977523,"title":"俄罗斯的著名建筑下藏着什么？","images":["http://pic4.zhimg.com/164ab948a0abdb79e010676e626f984f.jpg"],"type":2},{"id":7974023,"title":"Facebook 放大招？全新\u201cReaction\u201d社交功能上线！","images":["http://pic2.zhimg.com/7ad4fa62c59d8d600ec1d49e23068ff9.jpg"],"type":2},{"id":7974013,"title":"她从女性的视角展示设计的独特魅力","images":["http://pic4.zhimg.com/c1ee29e241ae651280088346fa45610b.jpg"],"type":2},{"id":6763022,"title":"几个超厉害的数据可视化工具","type":0},{"id":7968419,"title":"《疯狂动物城》里的细节，一遍肯定看不全！","images":["http://pic1.zhimg.com/2e45a64eed33a13a4c062e98c622e044.jpg"],"type":2},{"id":7968334,"title":"寥寥几笔画出一幅有意思的猫","images":["http://pic2.zhimg.com/4c73108319fbf938f4e86a15cdfecbad.jpg"],"type":0}]
     * color : 62140
     * description : 好设计需要打磨和研习，我们分享灵感和路径
     * name : 设计日报
     * background : http://p3.zhimg.com/ff/15/ff150eef63a48f0d1dafb77e62610a9f.jpg
     * image : http://p2.zhimg.com/98/dd/98dd8dcec0186ffba8d8e298255765e7.jpg
     * editors : [{"id":22,"bio":"PhD Researcher in Design","name":"Fanxtastic","avatar":"http://pic2.zhimg.com/e19f362d5_m.jpg","url":"http://www.zhihu.com/people/fanxtastic"},{"id":56,"bio":"产品设计师 @华兴资本","name":"星玫","avatar":"http://pic4.zhimg.com/de2ab67cf_m.jpg","url":"http://www.zhihu.com/people/starose"}]
     * image_source :
     */
    private List<StoriesEntity> stories;
    private int color;
    private String description;
    private String name;
    private String background;
    private String image;
    private List<EditorsEntity> editors;
    private String image_source;

    public void setStories(List<StoriesEntity> stories) {
        this.stories = stories;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setEditors(List<EditorsEntity> editors) {
        this.editors = editors;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

    public List<StoriesEntity> getStories() {
        return stories;
    }

    public int getColor() {
        return color;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getBackground() {
        return background;
    }

    public String getImage() {
        return image;
    }

    public List<EditorsEntity> getEditors() {
        return editors;
    }

    public String getImage_source() {
        return image_source;
    }

    public class StoriesEntity {
        /**
         * id : 8032481
         * title : 2016年“世界最美的书”获奖作品完整版公布
         * images : ["http://pic1.zhimg.com/1f17f33f9d1cbf323018fbcec047d624.jpg"]
         * type : 2
         */
        private int id;
        private String title;
        private List<String> images;
        private int type;

        public void setId(int id) {
            this.id = id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public List<String> getImages() {
            return images;
        }

        public int getType() {
            return type;
        }
    }

    public class EditorsEntity {
        /**
         * id : 22
         * bio : PhD Researcher in Design
         * name : Fanxtastic
         * avatar : http://pic2.zhimg.com/e19f362d5_m.jpg
         * url : http://www.zhihu.com/people/fanxtastic
         */
        private int id;
        private String bio;
        private String name;
        private String avatar;
        private String url;

        public void setId(int id) {
            this.id = id;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getId() {
            return id;
        }

        public String getBio() {
            return bio;
        }

        public String getName() {
            return name;
        }

        public String getAvatar() {
            return avatar;
        }

        public String getUrl() {
            return url;
        }
    }

}
