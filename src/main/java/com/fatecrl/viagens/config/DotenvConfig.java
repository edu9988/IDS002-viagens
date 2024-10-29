package com.fatecrl.viagens.config;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.context.annotation.Configuration;

@Configuration
public class DotenvConfig {

    @PostConstruct
    public void loadEnv() {
        String dbPw = System.getenv( "DB_PW" );
        if( dbPw != null ){
            System.out.println( "\nVariable DB_PW:"+dbPw+", using it\n" );
            System.setProperty( "DB_PW" , dbPw );
        }
        else{
            System.out.println( "\nVariable DB_PW null, trying .env\n" );
            try {
                List<String> lines = Files.readAllLines(Paths.get(".env"));
                for (String line : lines) {
                    if (!line.trim().isEmpty() && !line.trim().startsWith("#")) {
                        String[] parts = line.split("=", 2);
                        if (parts.length == 2) {
                            System.setProperty(parts[0], parts[1]);
                        }
                        else
                            System.setProperty(parts[0], parts[1]);
                    }
                }
            }
            catch (IOException e) {
                throw new RuntimeException("Could not read .env file", e);
            }
        }
    }
}
