package com.orangeTree.bigserj.data.net;


import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import static com.orangeTree.bigserj.data.ConstantsData.*;

public class CurrentObservationDeserializer<T> implements JsonDeserializer<T> {
    @Override
    public T deserialize(JsonElement je, Type type, JsonDeserializationContext jdc)
            throws JsonParseException
    {
        JsonElement current_observation = je.getAsJsonObject().get(CURRENT_OBSERVATION);

        return new Gson().fromJson(current_observation, type);
    }
}
