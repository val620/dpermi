package com.dpermi.domain;

import lombok.Data;

/**
 * Created by val620@126.com on 2018/7/19.
 */
@Data
public class TreeNode {

    private String id;
    private String text;
    private String icon;
    private String parent;
    private Object children;
    private State state;

    @Data
    public class State {
        public State(boolean opened, boolean disabled, boolean selected) {
            this.opened = opened;
            this.disabled = disabled;
            this.selected = selected;
        }

        private boolean opened;
        private boolean disabled;
        private boolean selected;
    }
}
