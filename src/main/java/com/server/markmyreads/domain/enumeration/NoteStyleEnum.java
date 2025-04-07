package com.server.markmyreads.domain.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NoteStyleEnum {

    CLASSIC("""
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
    """),

    SCRATCH("""
        <style>
            body { 
                font-family: 'Caveat', cursive; 
                font-size: 20px; 
                line-height: 1.6; 
                background-color: #FFF8E7; 
                color: #2B2B2B; 
                padding: 30px; 
                max-width: 800px; 
                margin: auto;
                background-image: url('data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" width="100" height="100" opacity="0.05"><path d="M10 10 L90 90 M90 10 L10 90" stroke="#ccc" stroke-width="1"/></svg>'); 
                background-repeat: repeat;
            }
            h1, h2, h3 { 
                font-family: 'Permanent Marker', cursive; /
                color: #D9534F; /* Vermelho rabiscado */
                font-weight: normal; 
                text-align: center; 
                letter-spacing: 1px;
                text-decoration: underline wavy #D9534F; 
                margin-bottom: 20px;
            }
            p { 
                text-align: justify; 
                margin-bottom: 25px; 
                border: 1px solid #999; /
                border-radius: 5px; 
                padding: 10px; 
                box-shadow: 2px 2px 5px rgba(0,0,0,0.1); 
                background: rgba(255, 255, 255, 0.8); 
            }
            blockquote { 
                font-style: italic; 
                border-left: 3px dashed #5BC0DE; 
                padding-left: 15px; 
                color: #444; 
                background: rgba(240, 248, 255, 0.5); 
                margin: 20px 0;
            }
        </style>
    """),

    DARK_MODE("""
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
    """),

    VINTAGE_JOURNAL("""
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
    """);

    private final String css;
}
