package org.lybaobei.common;
public class Constants {
    public interface Expire {

        static final int DAY1 = 86400;
        static final int DAY7 = 604800;
        static final int HOUR12 = 43200;
        static final int HOUR=3600;
        static final int HALF_AN_HOUR=1800;
        static final int MINUTE=60;
    }
    
    public interface UserStatus {
        static final int NORMAL = 1;
        static final int LOCKED = 2;
        static final int INVALID = 3;

        static final int LOCKCNT = 3;
    }
    
    public interface OrgStatus {
        static final int NORMAL = 1;
        static final int INVALID = 2;
    }
    
    public interface RoleStatus {
        static final int NORMAL = 1;
        static final int INVALID = 2;
    }
    
    public interface MenuStatus {
        static final int NORMAL = 1;
        static final int INVALID = 2;
    }
    
    public interface MenuType {
        static final int DIRECTOR = 1;
        static final int MENUED = 2;
        static final int BUTTONS = 3;
    }
}
