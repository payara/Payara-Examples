/*
 *
 * Copyright (c) 2019 Payara Foundation and/or its affiliates. All rights reserved.
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
package fish.payara.zipkin.tracer.lib;

import brave.Tracing;
import brave.opentracing.BraveTracer;
import brave.propagation.B3Propagation;
import brave.propagation.ExtraFieldPropagation;
import brave.propagation.ExtraFieldPropagation.Factory;
import io.opentracing.ScopeManager;
import io.opentracing.Span;
import io.opentracing.SpanContext;
import io.opentracing.propagation.Format;
import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;
import java.util.Arrays;
import zipkin2.reporter.AsyncReporter;
import zipkin2.reporter.okhttp3.OkHttpSender;

/**
 *
 * A Wrapper for Zipkin Tracer to allow it to be loaded as a service in
 * Payara Server
 * 
 * @author Cuba Stanley
 */
public class ZipkinTracerWrapper implements io.opentracing.Tracer {
    
    private static Tracer wrappedTracer;
    
    public ZipkinTracerWrapper() {
        setUpTracer();
    }
    
    private synchronized void setUpTracer() {
        if(wrappedTracer == null) {
            
            OkHttpSender sender = OkHttpSender.create("http://localhost:9411/api/v2/spans");
            AsyncReporter spanReporter = AsyncReporter.create(sender);
            
            Factory propagationFactory = ExtraFieldPropagation.newFactoryBuilder(B3Propagation.FACTORY)
                                                              .addPrefixedFields("baggage-", Arrays.asList("country-code", "user-id"))
                                                              .build();
            
            Tracing braveTracing = Tracing.newBuilder()
                                          .localServiceName("zipkin-test")
                                          .propagationFactory(propagationFactory)
                                          .spanReporter(spanReporter)
                                          .build();
            try {
                wrappedTracer = BraveTracer.create(braveTracing);
                GlobalTracer.register(wrappedTracer);
            } catch(Exception e) {
                System.out.print("There was an error initialising: \n");
                e.printStackTrace();
            }
        }
    }  
    
    @Override
    public ScopeManager scopeManager() {
        return wrappedTracer.scopeManager();
    }

    @Override
    public Span activeSpan() {
        return wrappedTracer.activeSpan();
    }

    @Override
    public SpanBuilder buildSpan(String string) {
        return wrappedTracer.buildSpan(string);
    }

    @Override
    public <C> void inject(SpanContext sc, Format<C> format, C c) {
        wrappedTracer.inject(sc, format, c);
    }

    @Override
    public <C> SpanContext extract(Format<C> format, C c) {
        return wrappedTracer.extract(format, c);
    }
}
