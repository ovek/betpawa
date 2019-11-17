package betpawa.grpcserver.generated;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.25.0)",
    comments = "Source: Balance.proto")
public final class BalanceServiceGrpc {

  private BalanceServiceGrpc() {}

  public static final String SERVICE_NAME = "BalanceService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<BalanceOuterClass.BalanceRequest,
      BalanceOuterClass.BalanceResponse> getBalanceMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Balance",
      requestType = BalanceOuterClass.BalanceRequest.class,
      responseType = BalanceOuterClass.BalanceResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<BalanceOuterClass.BalanceRequest,
      BalanceOuterClass.BalanceResponse> getBalanceMethod() {
    io.grpc.MethodDescriptor<BalanceOuterClass.BalanceRequest, BalanceOuterClass.BalanceResponse> getBalanceMethod;
    if ((getBalanceMethod = BalanceServiceGrpc.getBalanceMethod) == null) {
      synchronized (BalanceServiceGrpc.class) {
        if ((getBalanceMethod = BalanceServiceGrpc.getBalanceMethod) == null) {
          BalanceServiceGrpc.getBalanceMethod = getBalanceMethod =
              io.grpc.MethodDescriptor.<BalanceOuterClass.BalanceRequest, BalanceOuterClass.BalanceResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Balance"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  BalanceOuterClass.BalanceRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  BalanceOuterClass.BalanceResponse.getDefaultInstance()))
              .setSchemaDescriptor(new BalanceServiceMethodDescriptorSupplier("Balance"))
              .build();
        }
      }
    }
    return getBalanceMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static BalanceServiceStub newStub(io.grpc.Channel channel) {
    return new BalanceServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static BalanceServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new BalanceServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static BalanceServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new BalanceServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class BalanceServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void balance(BalanceOuterClass.BalanceRequest request,
        io.grpc.stub.StreamObserver<BalanceOuterClass.BalanceResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getBalanceMethod(), responseObserver);
    }

    @Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getBalanceMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                BalanceOuterClass.BalanceRequest,
                BalanceOuterClass.BalanceResponse>(
                  this, METHODID_BALANCE)))
          .build();
    }
  }

  /**
   */
  public static final class BalanceServiceStub extends io.grpc.stub.AbstractStub<BalanceServiceStub> {
    private BalanceServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private BalanceServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected BalanceServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new BalanceServiceStub(channel, callOptions);
    }

    /**
     */
    public void balance(BalanceOuterClass.BalanceRequest request,
        io.grpc.stub.StreamObserver<BalanceOuterClass.BalanceResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getBalanceMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class BalanceServiceBlockingStub extends io.grpc.stub.AbstractStub<BalanceServiceBlockingStub> {
    private BalanceServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private BalanceServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected BalanceServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new BalanceServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public BalanceOuterClass.BalanceResponse balance(BalanceOuterClass.BalanceRequest request) {
      return blockingUnaryCall(
          getChannel(), getBalanceMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class BalanceServiceFutureStub extends io.grpc.stub.AbstractStub<BalanceServiceFutureStub> {
    private BalanceServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private BalanceServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected BalanceServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new BalanceServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<BalanceOuterClass.BalanceResponse> balance(
        BalanceOuterClass.BalanceRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getBalanceMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_BALANCE = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final BalanceServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(BalanceServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_BALANCE:
          serviceImpl.balance((BalanceOuterClass.BalanceRequest) request,
              (io.grpc.stub.StreamObserver<BalanceOuterClass.BalanceResponse>) responseObserver);
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

  private static abstract class BalanceServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    BalanceServiceBaseDescriptorSupplier() {}

    @Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return BalanceOuterClass.getDescriptor();
    }

    @Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("BalanceService");
    }
  }

  private static final class BalanceServiceFileDescriptorSupplier
      extends BalanceServiceBaseDescriptorSupplier {
    BalanceServiceFileDescriptorSupplier() {}
  }

  private static final class BalanceServiceMethodDescriptorSupplier
      extends BalanceServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    BalanceServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (BalanceServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new BalanceServiceFileDescriptorSupplier())
              .addMethod(getBalanceMethod())
              .build();
        }
      }
    }
    return result;
  }
}
