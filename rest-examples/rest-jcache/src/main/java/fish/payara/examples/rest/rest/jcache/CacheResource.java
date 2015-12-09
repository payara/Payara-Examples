/*

 DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.

 Copyright (c) 2015 C2B2 Consulting Limited. All rights reserved.

 The contents of this file are subject to the terms of the Common Development
 and Distribution License("CDDL") (collectively, the "License").  You
 may not use this file except in compliance with the License.  You can
 obtain a copy of the License at
 https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 or packager/legal/LICENSE.txt.  See the License for the specific
 language governing permissions and limitations under the License.

 When distributing the software, include this License Header Notice in each
 file and include the License file at packager/legal/LICENSE.txt.
 */
package fish.payara.examples.rest.rest.jcache;

import javax.cache.annotation.CacheKey;
import javax.cache.annotation.CachePut;
import javax.cache.annotation.CacheResult;
import javax.cache.annotation.CacheValue;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

/**
 * REST Web Service
 *
 * @author steve
 */
@Path("cache")
public class CacheResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of CacheResource
     */
    public CacheResource() {
    }

    /**
     * Retrieves representation of an instance of fish.payara.examples.rest.rest.jcache.CacheResource
     * @param The key for the JSON object
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @CacheResult(cacheName = "rest-jcache")
    public String getJSON(@QueryParam("key") @CacheKey String key ) {
        return "helloworld";
    }

    /**
     * PUT method for updating or creating an instance of CacheResource
     * The JSON is stored in the Payara Micro distributed Cache
     * @param key Cache Key for the JSON data
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @CachePut(cacheName = "rest-jcache") 
    public void putJSON(@QueryParam("key") @CacheKey String key, @CacheValue String content ) {
    }
}
