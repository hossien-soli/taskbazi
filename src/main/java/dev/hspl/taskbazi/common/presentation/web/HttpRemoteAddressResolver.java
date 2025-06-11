package dev.hspl.taskbazi.common.presentation.web;

import jakarta.servlet.http.HttpServletRequest;

// if you want to leave spring app behind a reverse proxy or load balancing you should change the implementation to...
// ...finding the request ip of the actual client-user somehow

public interface HttpRemoteAddressResolver {
    String resolve(HttpServletRequest request);
}
