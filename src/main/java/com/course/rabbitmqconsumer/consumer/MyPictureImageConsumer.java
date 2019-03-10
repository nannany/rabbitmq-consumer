package com.course.rabbitmqconsumer.consumer;

import java.io.IOException;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.course.rabbitmqconsumer.consumer.entity.Picture;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;

@Service
public class MyPictureImageConsumer {

	private ObjectMapper objectMapper = new ObjectMapper();

	@RabbitListener(queues = "q.mypicture.image")
	public void listen(Message message, Channel channel) throws IOException, JsonParseException, JsonMappingException {
		var p = objectMapper.readValue(message.getBody(), Picture.class);

		if (p.getSize() > 9000) {
//			throw new AmqpRejectAndDontRequeueException("picture size too large" + p);
			channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
		} else {
			System.out.println("Creating thumbnail & publishing:" + p);
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		}

	}
}
