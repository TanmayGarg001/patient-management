package com.psbill.patientservicebilling.grpc;

import com.healthcare.patientservice.billing.BillingRequest;
import com.healthcare.patientservice.billing.BillingResponse;
import com.healthcare.patientservice.billing.BillingServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class BillingGrpcService extends BillingServiceGrpc.BillingServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(BillingGrpcService.class);

    @Override
    public void createBillingAccount(BillingRequest request, StreamObserver<BillingResponse> responseObserver) {
        //stream observer allows us to send multiple responses if needed and handle async communication, example: chat applications
        //Rest use single request-response model but gRPC can handle streaming scenarios, using stream observers.
        log.info("createBillingAccount request received: {}", request.toString());

        //Buisness logic to create billing account would go here


        //This simulates a successful billing account creation
        BillingResponse response = BillingResponse.newBuilder().setAccountId("12345").setStatus("SUCCESS").build();
        responseObserver.onNext(response);//send response back to client, is needed as we can send multiple response if needed
        responseObserver.onCompleted();//indicate that the response is complete, is needed because we need to tell the client that no more messages will be sent

    }


}