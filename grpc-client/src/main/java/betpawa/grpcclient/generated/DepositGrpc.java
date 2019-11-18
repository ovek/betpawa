package betpawa.grpcclient.generated;

import io.grpc.stub.ClientCalls;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.25.0)",
    comments = "Source: Deposit.proto")
public final class DepositGrpc {

  private DepositGrpc() {}

  public static final String SERVICE_NAME = "Deposit";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<DepositOuterClass.DepositRequest,
      DepositOuterClass.DepositResponse> getDepositMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Deposit",
      requestType = DepositOuterClass.DepositRequest.class,
      responseType = DepositOuterClass.DepositResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<DepositOuterClass.DepositRequest,
      DepositOuterClass.DepositResponse> getDepositMethod() {
    io.grpc.MethodDescriptor<DepositOuterClass.DepositRequest, DepositOuterClass.DepositResponse> getDepositMethod;
    if ((getDepositMethod = DepositGrpc.getDepositMethod) == null) {
      synchronized (DepositGrpc.class) {
        if ((getDepositMethod = DepositGrpc.getDepositMethod) == null) {
          DepositGrpc.getDepositMethod = getDepositMethod =
              io.grpc.MethodDescriptor.<DepositOuterClass.DepositRequest, DepositOuterClass.DepositResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Deposit"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  DepositOuterClass.DepositRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  DepositOuterClass.DepositResponse.getDefaultInstance()))
              .setSchemaDescriptor(new DepositMethodDescriptorSupplier("Deposit"))
              .build();
        }
      }
    }
    return getDepositMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static DepositStub newStub(io.grpc.Channel channel) {
    return new DepositStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static DepositBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new DepositBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static DepositFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new DepositFutureStub(channel);
  }

  /**
   */
  public static abstract class DepositImplBase implements io.grpc.BindableService {

    /**
     */
    public void deposit(DepositOuterClass.DepositRequest request,
        io.grpc.stub.StreamObserver<DepositOuterClass.DepositResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getDepositMethod(), responseObserver);
    }

    @Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getDepositMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                DepositOuterClass.DepositRequest,
                DepositOuterClass.DepositResponse>(
                  this, METHODID_DEPOSIT)))
          .build();
    }
  }

  /**
   */
  public static final class DepositStub extends io.grpc.stub.AbstractStub<DepositStub> {
    private DepositStub(io.grpc.Channel channel) {
      super(channel);
    }

    private DepositStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected DepositStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new DepositStub(channel, callOptions);
    }

    /**
     */
    public void deposit(DepositOuterClass.DepositRequest request,
        io.grpc.stub.StreamObserver<DepositOuterClass.DepositResponse> responseObserver) {
      ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDepositMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class DepositBlockingStub extends io.grpc.stub.AbstractStub<DepositBlockingStub> {
    private DepositBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private DepositBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected DepositBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new DepositBlockingStub(channel, callOptions);
    }

    /**
     */
    public DepositOuterClass.DepositResponse deposit(DepositOuterClass.DepositRequest request) {
      return blockingUnaryCall(
          getChannel(), getDepositMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class DepositFutureStub extends io.grpc.stub.AbstractStub<DepositFutureStub> {
    private DepositFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private DepositFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected DepositFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new DepositFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<DepositOuterClass.DepositResponse> deposit(
        DepositOuterClass.DepositRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getDepositMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_DEPOSIT = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final DepositImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(DepositImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_DEPOSIT:
          serviceImpl.deposit((DepositOuterClass.DepositRequest) request,
              (io.grpc.stub.StreamObserver<DepositOuterClass.DepositResponse>) responseObserver);
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

  private static abstract class DepositBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    DepositBaseDescriptorSupplier() {}

    @Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return DepositOuterClass.getDescriptor();
    }

    @Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Deposit");
    }
  }

  private static final class DepositFileDescriptorSupplier
      extends DepositBaseDescriptorSupplier {
    DepositFileDescriptorSupplier() {}
  }

  private static final class DepositMethodDescriptorSupplier
      extends DepositBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    DepositMethodDescriptorSupplier(String methodName) {
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
      synchronized (DepositGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new DepositFileDescriptorSupplier())
              .addMethod(getDepositMethod())
              .build();
        }
      }
    }
    return result;
  }
}
