package org.autojs.phonefarm.tool;

import com.stardust.app.GlobalAppContext;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class Observers {

    private static final Consumer CONSUMER = ignored -> {

    };

    private static final Consumer<Throwable> TOAST_MESSAGE = e -> {
        e.printStackTrace();
        GlobalAppContext.toast(e.getMessage());
    };


    @SuppressWarnings("unchecked")
    public static <T> Consumer<T> emptyConsumer() {
        return CONSUMER;
    }

    public static Consumer<Throwable> toastMessage() {
        return TOAST_MESSAGE;
    }

    public  static <T> Observer<T> emptyObserver() {
        return new Observer<T>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(T t) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

    }
}
