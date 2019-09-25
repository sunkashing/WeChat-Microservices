# Group Chat Service
Scalable Websocket-based chat service, allowing retrieving user profile information in details

## Endpoints
###### HTTP endpoints
"/health" (used for heath check and readiness check)
"/getallprofiles" (get all the user profiles)
"/getallmessages" (get all the previous chat messages)
"/profile?username=" (get a profile based on username)

###### Websocket endpoints
"/message" (websocket endpoint for chat messages)
