package org.mobiletrain.android37_materialdesigndemo.bean;

/**
 * Created by Administrator on 2016/3/24.
 */
public class SpecialDetail {
        private String title;
        private String digest;
        private String url;
        private int replyCount;
        private String imgsrc;

        public SpecialDetail(String title, String digest, String url, int replyCount,String imgsrc) {
            this.title = title;
            this.digest = digest;
            this.url = url;
            this.replyCount = replyCount;
            this.imgsrc = imgsrc;
        }

        public String getImgsrc() {
            return imgsrc;
        }

        public void setImgsrc(String imgsrc) {
            this.imgsrc = imgsrc;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDigest() {
            return digest;
        }

        public void setDigest(String digest) {
            this.digest = digest;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getReplyCount() {
            return replyCount;
        }

        public void setReplyCount(int replyCount) {
            this.replyCount = replyCount;
        }

}

