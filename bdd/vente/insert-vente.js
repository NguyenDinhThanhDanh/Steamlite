let insertVente= [
    {
        _id:"1",
        NomJeux:"1",
        client: [
            {
                idClient:"2",
                dateAchat:"2022-01-02"
            },
            {
                idClient:"3",
                dateAchat:"2022-03-09"
            },
        ]
    },
    {
        _id:"2",
        NomJeux:"Elden Ring",
        client: [
            {
                idClient:"2",
                dateAchat:"2022-01-02"
            },
            {
                idClient:"4",
                dateAchat:"2021-03-09"
            },
        ]
    }
]
//db.service_vente.insert({ _id:"1",NomJeux:"1",client:[{idClient:"2",dateAchat:"2022-01-02"},{idClient:"3",dateAchat:"2022-03-09"}] }  )
