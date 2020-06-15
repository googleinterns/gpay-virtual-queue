package com.google.gpay.virtualqueue.backendservice.proto;

import java.util.ArrayList;
import java.util.List;

import com.google.gpay.virtualqueue.backendservice.model.Token;

import lombok.Data;
import lombok.Setter;

@Data
@Setter
public class GetTokensForShopResponse {
    private List<Token> tokenList = new ArrayList<>();
}
