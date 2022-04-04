/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2022 Payara Foundation and/or its affiliates. All rights reserved.
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
package fish.payara.samples.grpc.protobufsources;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.43.1)",
    comments = "Source: payara_ejb.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class PayaraServiceGrpc {

  private PayaraServiceGrpc() {}

  public static final String SERVICE_NAME = "fish.payara.samples.grpc.PayaraService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<PayaraReq,
          PayaraResp> getCommunicateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "communicate",
      requestType = PayaraReq.class,
      responseType = PayaraResp.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<PayaraReq,
          PayaraResp> getCommunicateMethod() {
    io.grpc.MethodDescriptor<PayaraReq, PayaraResp> getCommunicateMethod;
    if ((getCommunicateMethod = PayaraServiceGrpc.getCommunicateMethod) == null) {
      synchronized (PayaraServiceGrpc.class) {
        if ((getCommunicateMethod = PayaraServiceGrpc.getCommunicateMethod) == null) {
          PayaraServiceGrpc.getCommunicateMethod = getCommunicateMethod =
              io.grpc.MethodDescriptor.<PayaraReq, PayaraResp>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "communicate"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  PayaraReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  PayaraResp.getDefaultInstance()))
              .setSchemaDescriptor(new PayaraServiceMethodDescriptorSupplier("communicate"))
              .build();
        }
      }
    }
    return getCommunicateMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PayaraServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PayaraServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PayaraServiceStub>() {
        @Override
        public PayaraServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PayaraServiceStub(channel, callOptions);
        }
      };
    return PayaraServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PayaraServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PayaraServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PayaraServiceBlockingStub>() {
        @Override
        public PayaraServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PayaraServiceBlockingStub(channel, callOptions);
        }
      };
    return PayaraServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static PayaraServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PayaraServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PayaraServiceFutureStub>() {
        @Override
        public PayaraServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PayaraServiceFutureStub(channel, callOptions);
        }
      };
    return PayaraServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class PayaraServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void communicate(PayaraReq request,
                            io.grpc.stub.StreamObserver<PayaraResp> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCommunicateMethod(), responseObserver);
    }

    @Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getCommunicateMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                      PayaraReq,
                      PayaraResp>(
                  this, METHODID_COMMUNICATE)))
          .build();
    }
  }

  /**
   */
  public static final class PayaraServiceStub extends io.grpc.stub.AbstractAsyncStub<PayaraServiceStub> {
    private PayaraServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected PayaraServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PayaraServiceStub(channel, callOptions);
    }

    /**
     */
    public void communicate(PayaraReq request,
                            io.grpc.stub.StreamObserver<PayaraResp> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCommunicateMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class PayaraServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<PayaraServiceBlockingStub> {
    private PayaraServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected PayaraServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PayaraServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public PayaraResp communicate(PayaraReq request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCommunicateMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class PayaraServiceFutureStub extends io.grpc.stub.AbstractFutureStub<PayaraServiceFutureStub> {
    private PayaraServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected PayaraServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PayaraServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<PayaraResp> communicate(
        PayaraReq request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCommunicateMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_COMMUNICATE = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final PayaraServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(PayaraServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_COMMUNICATE:
          serviceImpl.communicate((PayaraReq) request,
              (io.grpc.stub.StreamObserver<PayaraResp>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @Override
    @SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class PayaraServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    PayaraServiceBaseDescriptorSupplier() {}

    @Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return PayaraProto.getDescriptor();
    }

    @Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("PayaraService");
    }
  }

  private static final class PayaraServiceFileDescriptorSupplier
      extends PayaraServiceBaseDescriptorSupplier {
    PayaraServiceFileDescriptorSupplier() {}
  }

  private static final class PayaraServiceMethodDescriptorSupplier
      extends PayaraServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    PayaraServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (PayaraServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PayaraServiceFileDescriptorSupplier())
              .addMethod(getCommunicateMethod())
              .build();
        }
      }
    }
    return result;
  }
}
