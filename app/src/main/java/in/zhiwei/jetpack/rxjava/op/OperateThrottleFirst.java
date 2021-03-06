package in.zhiwei.jetpack.rxjava.op;

import in.zhiwei.jetpack.rxjava.op.base.BaseOp;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

/**
 * Author: zhiwei.
 * Date: 2018/11/7 0007,20:15.
 */
public class OperateThrottleFirst extends BaseOp {

    private static String TAG = "OperateThrottleFirst";

    /*
     * ThrottleFirst操作符，会在指定的时间间隔之内，发送第一条指令
     */
    public static void doSome() {
        getObservable()
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(TAG, ""));
    }

    /*
        d.isDisposed():false
        a
        c
        e
        onComplete
     */
    private static Observable<String> getObservable() {
        return Observable.create(emitter -> {
            // send events with simulated time wait
            emitter.onNext("a"); // skip
            Thread.sleep(400);
            emitter.onNext("b"); // deliver
            Thread.sleep(505);
            emitter.onNext("c"); // skip
            Thread.sleep(100);
            emitter.onNext("d"); // deliver，3和4属于同一个500ms间隔
            Thread.sleep(605);
            emitter.onNext("e"); // deliver
            Thread.sleep(510);
            emitter.onComplete();
        });
    }

}
