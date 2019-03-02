package org.open.budget.costlocator.api;

import com.google.gson.*;
import org.open.budget.costlocator.entity.Tender;

import java.lang.reflect.Type;

public class TenderDeserializer implements JsonDeserializer<Tender> {
    @Override
    public Tender deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        Tender tender = gson.fromJson(json, Tender.class);
        tender.setTenderDetail(tender.getTenderDetails().get(0));
        return tender;
    }
}
