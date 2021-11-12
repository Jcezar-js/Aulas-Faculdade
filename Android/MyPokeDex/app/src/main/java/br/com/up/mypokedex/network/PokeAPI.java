package br.com.up.mypokedex.network;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import br.com.up.mypokedex.MainActivity;
import br.com.up.mypokedex.model.Pokemon;

public class PokeAPI {

    public void getPokemon(PokeAPIListener listener){

        ConnectionAsyncTask connectionAsyncTask =  new ConnectionAsyncTask(new ConnectionAsyncTask.ConnectionListener() {

            @Override
            public void onRequestFinish(JSONObject object) {
                ArrayList<Pokemon>pokemons = new ArrayList<>();

                try {

                    JSONArray results = object.getJSONArray("results");

                    for (int index = 0 ; index < results.length(); index++){

                        JSONObject objectPokemon = results.getJSONObject(index);

                        String name = objectPokemon.getString("name");
                        String url = objectPokemon.getString("url");
                        String id = url.replace("https://pokeapi.co/api/v2/pokemon/","");
                        id = id.replace("/","");
                        String image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/" + id + ".png";
                        Pokemon pokemon = new Pokemon(Integer.parseInt(id), name,image);

                        pokemons.add(pokemon);
                    }
                }catch (Exception e){
                }

                listener.onPokemonsMapperFinish(pokemons);
            }
        });
        connectionAsyncTask.execute("https://pokeapi.co/api/v2/pokemon?limit=1500");
    }

    public interface PokeAPIListener{
        void onPokemonsMapperFinish(ArrayList<Pokemon> pokemons);
    }
}