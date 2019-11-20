/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fish.payara.zipkin.tracer.lib;

import brave.Tracing;
import brave.opentracing.BraveTracer;
import brave.propagation.B3Propagation;
import brave.propagation.ExtraFieldPropagation;
import brave.propagation.ExtraFieldPropagation.Factory;
import io.opentracing.Scope;
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
