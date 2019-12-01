package net.siudek;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * Bindable interface with one input channel.
 *
 * @see org.springframework.cloud.stream.annotation.EnableBinding
 */
public interface MySink {

	/**
	 * Input channel name.
	 */
	String INPUT = "myTopicInput";

	/**
	 * @return input channel.
	 */
	@Input(MySink.INPUT)
	SubscribableChannel input();

}