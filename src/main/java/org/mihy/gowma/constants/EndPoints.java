package org.mihy.gowma.constants;

/**
 * Created by gdeepu on 19/11/17.
 */
public final class EndPoints {


    public static final String API_V1_ROOT = "api/v1";

    private EndPoints() {

    }

    public static  final class PathVariable{

        public static final String USER_ID ="userId";
    }

    public static  final class PathVariableTemplate{

        public static final String USER_ID_TEMPLATE ="{"+PathVariable.USER_ID+"}";
    }

    public static  final class QueryParam{

        public static final String USER_ID_TEMPLATE ="{"+PathVariable.USER_ID+"}";
    }

    public static final class User {


        public static final String ROOT = API_V1_ROOT + "/user";
        public static final String USER_WITH_ID = ROOT + "/" + PathVariableTemplate.USER_ID_TEMPLATE;




    }
}
