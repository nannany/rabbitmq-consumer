package com.course.rabbitmqconsumer.consumer;

import java.io.IOException;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.course.rabbitmqconsumer.consumer.entity.Picture;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PictureLogConsumer {

	private ObjectMapper objectMapper = new ObjectMapper();

	@RabbitListener(queues = "q.picture.log")
	public void listen(String message) throws IOException, JsonParseException, JsonMappingException {
		var p = objectMapper.readValue(message, Picture.class);
		System.out.println("Logging large vector :" + p);
	}
}
