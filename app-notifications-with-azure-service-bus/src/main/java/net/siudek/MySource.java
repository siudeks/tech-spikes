package net.siudek;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * Bindable interface with one output channel.
 * 
 * @see org.springframework.cloud.stream.annotation.EnableBinding
 */
public interface MySource {

	String OUTPUT = "myTopicOutput";

	/**
	 * @return output channel
	 */
	@Output(MySource.OUTPUT)
	MessageChannel output();

}