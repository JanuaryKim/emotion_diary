export const IndexedDBConfig = {
  name: "emotion_diary",
  version: 1,
  objectStoresMeta: [
    {
      store: "diary",
      storeConfig: { keyPath: "id", autoIncrement: true },
      storeSchema: [
        { name: "date", keypath: "date", options: { unique: false } },
        { name: "content", keypath: "content", options: { unique: false } },
        { name: "emotion", keypath: "emotion", options: { unique: false } },
        { name: "images", keypath: "images", options: { unique: false } },
      ],
    },
  ],
};
