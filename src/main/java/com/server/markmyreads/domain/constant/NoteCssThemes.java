package com.server.markmyreads.domain.constant;

public final class NoteCssThemes {

    private NoteCssThemes(){}

    private final String CLASSIC =
            """
            <style>
                body { 
                    font-family: "Georgia", serif; 
                    font-size: 18px; 
                    line-height: 1.8; 
                    background-color: #FAF3E0; 
                    color: #333; 
                    padding: 40px; 
                    max-width: 800px; 
                    margin: auto;
                }
                h1, h2, h3 { 
                    color: #8B4513; 
                    font-weight: bold; 
                    text-align: center; 
                }
                p { 
                    text-align: justify; 
                    margin-bottom: 20px; 
                }
                blockquote { 
                    font-style: italic; 
                    border-left: 4px solid #8B4513; 
                    padding-left: 15px; 
                    color: #555;
                }
            </style>
            """;


    private static final String DARK_MODE =
            """
            <style>
                body {
                    font-family: "Arial", sans-serif; 
                    font-size: 18px; 
                    line-height: 1.7; 
                    background-color: #1e1e1e; 
                    color: #d4d4d4; 
                    padding: 40px; 
                    max-width: 800px; 
                    margin: auto;
                }
                h1, h2, h3 { 
                    color: #ffcc00; 
                    text-align: center; 
                }
                p { 
                    text-align: justify; 
                    margin-bottom: 20px; 
                }
                blockquote { 
                    font-style: italic; 
                    border-left: 4px solid #ffcc00; 
                    padding-left: 15px; 
                    color: #f5f5f5;
                }
                code { 
                    background-color: #333; 
                    padding: 5px; 
                    border-radius: 3px; 
                    font-family: "Courier New", monospace;
                }
            </style>
            """;

    private static final String VINTAGE_JOURNAL =
            """
            <style>
            body {
                font-family: "Patrick Hand", cursive;
                font-size: 20px;
                line-height: 1.9;
                background-color: #fdf6e3;
                color: #5c3d2e;
                padding: 40px;
                max-width: 800px;
                margin: auto;
                border: 10px solid #d4a373;
                box-shadow: 5px 5px 15px rgba(0, 0, 0, 0.1);
            }
            h1, h2, h3 {
                color: #b85c38;
                text-align: center;
                text-transform: uppercase;
            }
            p {
                text-align: justify;
                margin-bottom: 20px;
            }
            blockquote {
                font-style: italic;
                background: #fff3cd;
                padding: 15px;
                border-left: 5px solid #b85c38;
                font-size: 18px;
            }
        </style>
        """;


}
