package com.wangbaiwan.gravity.java.designpattern.observer.jdk9;

import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.stream.Materializer;
import akka.stream.impl.ExtendedActorMaterializer;
import akka.stream.javadsl.JavaFlowSupport;
import io.reactivex.Flowable;
import org.reactivestreams.FlowAdapters;
import reactor.adapter.JdkFlowAdapter;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.Flow;

/**
 * 集成 akka  RxJava 实现响应式编程
 *
 * @author Administrator
 */
public class IntegrationApp
{
	private static ActorSystem actorSystem = ActorSystem.create();
	private static Materializer materialize = ExtendedActorMaterializer.apply(actorSystem);

	private static Flow.Publisher<Long> reactorPublisher()
	{
		Flux<Long> numberFlux = Flux.interval(Duration.ofSeconds(1));
		return JdkFlowAdapter.publisherToFlowPublisher(numberFlux);
	}

	private static Flow.Processor<Long, Object> akkaStreamProcessor()
	{
		akka.stream.javadsl.Flow<Long, Object, NotUsed> flow = akka.stream.javadsl.Flow.of(Long.class).map(i -> -i);
		return JavaFlowSupport.Flow.toProcessor(flow).run(materialize);
	}

	/**
	 * 集成 akka  RxJava 实现响应式编程
	 *
	 * @param args
	 */
	public static void main(String[] args)
	{
		var reactorPublisher = reactorPublisher();
		var akkaStreamProcessor = akkaStreamProcessor();
		reactorPublisher.subscribe(akkaStreamProcessor);
		Flowable.fromPublisher(FlowAdapters.toProcessor(akkaStreamProcessor)).subscribe(System.out::println);
	}
}
