# SimpleToolManagerBackend

This project uses Firebase for authentication and Firestore access. Firebase support is optional and disabled by default.

## Enabling Firebase

1. Generate a service account key from the Firebase console.
2. Save the key JSON as `serviceAccount.json` in the project root. The file is ignored by Git.
3. Uncomment the `firebase.credentials` line in `src/main/resources/application.properties` and set the path to your key file if different.

An example of the expected service account structure is provided in `serviceAccount.example.json`.
