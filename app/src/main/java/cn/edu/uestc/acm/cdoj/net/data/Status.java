package cn.edu.uestc.acm.cdoj.net.data;

import org.json.JSONObject;

/**
 * Created by qwe on 16-8-21.
 */
public class Status {
    public final static int LANGUAGE_C = 1, LANGUAGE_CPP = 2, LANGUAGE_JAVA = 3;
    public final static int OJ_WAIT = 0,
    OJ_AC =  1,
    OJ_PE =  2,
    OJ_TLE =  3,
    OJ_MLE =  4,
    OJ_WA =  5,
    OJ_OLE =  6,
    OJ_CE =  7,
    OJ_RE_SEGV =  8,
    OJ_RE_FPE =  9,
    OJ_RE_BUS = 10,
    OJ_RE_ABRT = 11,
    OJ_RE_UNKNOWN = 12,
    OJ_RF = 13,
    OJ_SE = 14,
    OJ_RE_JAVA = 15,
    OJ_JUDGING = 16,
    OJ_RUNNING = 17,
    OJ_REJUDGING =  18;
//    OJ_WAIT("Queuing"),                             // 0
//    OJ_AC("Accepted"),                              // 1
//    OJ_PE("Presentation Error on test $case"),      // 2
//    OJ_TLE("Time Limit Exceeded on test $case"),    // 3
//    OJ_MLE("Memory Limit Exceeded on test $case"),  // 4
//    OJ_WA("Wrong Answer on test $case"),            // 5
//    OJ_OLE("Output Limit Exceeded on test $case"),  // 6
//    OJ_CE("Compilation Error"),                     // 7
//    OJ_RE_SEGV("Runtime Error on test $case"),      // 8
//    OJ_RE_FPE("Runtime Error on test $case"),       // 9
//    OJ_RE_BUS("Runtime Error on test $case"),       // 10
//    OJ_RE_ABRT("Runtime Error on test $case"),      // 11
//    OJ_RE_UNKNOWN("Runtime Error on test $case"),   // 12
//    OJ_RF("Restricted Function on test $case"),     // 13
//    OJ_SE("System Error on test $case"),            // 14
//    OJ_RE_JAVA("Runtime Error on test $case"),      // 15
//    OJ_JUDGING("Queuing"),                          // 16
//    OJ_RUNNING("Running on test $case"),            // 17
//    OJ_REJUDGING("Queuing");                        // 18
}
