/*
 * DO NOT ALTER OR REMOTE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2015-2022 Payara Foundation and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://github.com/payara/Payara/blob/master/LICENSE.txt
 * See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at glassfish/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * The Payara Foundation designates this particular file as subject to the "Classpath"
 * exception as provided by the Payara Foundation in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package fish.payara.examples.rest.rest.jcache;

import javax.cache.annotation.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriInfo;

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


    /**
     * DELETE method for cache eviction
     * @param key Key for the entry to remove
     */
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @CacheRemove(cacheName = "rest-jcache")
    public void deleteJSON(@QueryParam("key") @CacheKey String key){ }



}
