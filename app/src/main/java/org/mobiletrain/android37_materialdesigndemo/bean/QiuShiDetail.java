package org.mobiletrain.android37_materialdesigndemo.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016-03-23.
 */
public class QiuShiDetail implements Serializable {

    /**
     * format : word
     * image : null
     * published_at : 1458687302
     * tag :
     * user : {"avatar_updated_at":1398919790,"uid":5461351,"last_visited_at":1356786189,"created_at":1356786189,"state":"active","last_device":"android_2.1.2","role":"n","login":"医然为你","id":5461351,"icon":"20140501124950.jpg"}
     * image_size : null
     * id : 115650707
     * votes : {"down":-140,"up":4319}
     * is_mine : false
     * created_at : 1458684216
     * content : 大学时，我们临床专业英语课都跟护士班在一起上课，一个护士班全是女同学，哪有时间听课，一次上了，正跟女生聊的火热，老师叫我：王同学，你在说什么呢。 我刚要说。 老师：请用英语回答我！！ 下课铃响了。 我：good bye！！？
     * state : publish
     * comments_count : 50
     * allow_comment : true
     * share_count : 14
     * type : hot
     */

    private ArticleEntity article;
    /**
     * article : {"format":"word","image":null,"published_at":1458687302,"tag":"","user":{"avatar_updated_at":1398919790,"uid":5461351,"last_visited_at":1356786189,"created_at":1356786189,"state":"active","last_device":"android_2.1.2","role":"n","login":"医然为你","id":5461351,"icon":"20140501124950.jpg"},"image_size":null,"id":115650707,"votes":{"down":-140,"up":4319},"is_mine":false,"created_at":1458684216,"content":"大学时，我们临床专业英语课都跟护士班在一起上课，一个护士班全是女同学，哪有时间听课，一次上了，正跟女生聊的火热，老师叫我：王同学，你在说什么呢。 我刚要说。 老师：请用英语回答我！！ 下课铃响了。 我：good bye！！？","state":"publish","comments_count":50,"allow_comment":true,"share_count":14,"type":"hot"}
     * err : 0
     */

    private int err;

    public void setArticle(ArticleEntity article) {
        this.article = article;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public ArticleEntity getArticle() {
        return article;
    }

    public int getErr() {
        return err;
    }

    public static class ArticleEntity {
        private String format;
        private Object image;
        private int published_at;
        private String tag;
        /**
         * avatar_updated_at : 1398919790
         * uid : 5461351
         * last_visited_at : 1356786189
         * created_at : 1356786189
         * state : active
         * last_device : android_2.1.2
         * role : n
         * login : 医然为你
         * id : 5461351
         * icon : 20140501124950.jpg
         */

        private UserEntity user;
        private Object image_size;
        private int id;
        /**
         * down : -140
         * up : 4319
         */

        private VotesEntity votes;
        private boolean is_mine;
        private int created_at;
        private String content;
        private String state;
        private int comments_count;
        private boolean allow_comment;
        private int share_count;
        private String type;

        public void setFormat(String format) {
            this.format = format;
        }

        public void setImage(Object image) {
            this.image = image;
        }

        public void setPublished_at(int published_at) {
            this.published_at = published_at;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public void setUser(UserEntity user) {
            this.user = user;
        }

        public void setImage_size(Object image_size) {
            this.image_size = image_size;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setVotes(VotesEntity votes) {
            this.votes = votes;
        }

        public void setIs_mine(boolean is_mine) {
            this.is_mine = is_mine;
        }

        public void setCreated_at(int created_at) {
            this.created_at = created_at;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setState(String state) {
            this.state = state;
        }

        public void setComments_count(int comments_count) {
            this.comments_count = comments_count;
        }

        public void setAllow_comment(boolean allow_comment) {
            this.allow_comment = allow_comment;
        }

        public void setShare_count(int share_count) {
            this.share_count = share_count;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getFormat() {
            return format;
        }

        public Object getImage() {
            return image;
        }

        public int getPublished_at() {
            return published_at;
        }

        public String getTag() {
            return tag;
        }

        public UserEntity getUser() {
            return user;
        }

        public Object getImage_size() {
            return image_size;
        }

        public int getId() {
            return id;
        }

        public VotesEntity getVotes() {
            return votes;
        }

        public boolean isIs_mine() {
            return is_mine;
        }

        public int getCreated_at() {
            return created_at;
        }

        public String getContent() {
            return content;
        }

        public String getState() {
            return state;
        }

        public int getComments_count() {
            return comments_count;
        }

        public boolean isAllow_comment() {
            return allow_comment;
        }

        public int getShare_count() {
            return share_count;
        }

        public String getType() {
            return type;
        }

        public static class UserEntity {
            private int avatar_updated_at;
            private int uid;
            private int last_visited_at;
            private int created_at;
            private String state;
            private String last_device;
            private String role;
            private String login;
            private int id;
            private String icon;

            public void setAvatar_updated_at(int avatar_updated_at) {
                this.avatar_updated_at = avatar_updated_at;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public void setLast_visited_at(int last_visited_at) {
                this.last_visited_at = last_visited_at;
            }

            public void setCreated_at(int created_at) {
                this.created_at = created_at;
            }

            public void setState(String state) {
                this.state = state;
            }

            public void setLast_device(String last_device) {
                this.last_device = last_device;
            }

            public void setRole(String role) {
                this.role = role;
            }

            public void setLogin(String login) {
                this.login = login;
            }

            public void setId(int id) {
                this.id = id;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public int getAvatar_updated_at() {
                return avatar_updated_at;
            }

            public int getUid() {
                return uid;
            }

            public int getLast_visited_at() {
                return last_visited_at;
            }

            public int getCreated_at() {
                return created_at;
            }

            public String getState() {
                return state;
            }

            public String getLast_device() {
                return last_device;
            }

            public String getRole() {
                return role;
            }

            public String getLogin() {
                return login;
            }

            public int getId() {
                return id;
            }

            public String getIcon() {
                return icon;
            }
        }

        public static class VotesEntity {
            private int down;
            private int up;

            public void setDown(int down) {
                this.down = down;
            }

            public void setUp(int up) {
                this.up = up;
            }

            public int getDown() {
                return down;
            }

            public int getUp() {
                return up;
            }
        }
    }
}
