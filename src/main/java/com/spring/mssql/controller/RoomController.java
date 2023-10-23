package com.spring.mssql.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * This is the controller for the Room class, 
 * where all the endpoints of this entity are implemented.
 * To make it a controller the class must be annotated with
 * {@link org.springframework.web.bind.annotation.ReastController @RestController}.
 * <br>
 * It is also specified the origins allowed for the CORS
 * with {@link org.springframework.web.bind.annotation.CrossOrigin @CrossOrigin},
 * and the first part of the path to call to retrieve the
 * information from an endpoint with
 * {@link org.springframework.web.bind.annotation.RequestMapping @RequestMapping}.
 * @since 1.0.0
 * @author fforfabio
 * @see
 * <ul>
 * <li><a href="https://spring.io/guides/gs/rest-service-cors/">Spring doc for CORS</a></li>
 * <li><a href="https://www.baeldung.com/spring-cors">CORS pt.2</a></li>
 * </ul>
 **/
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/RoomsApi")
public class RoomController {

}
