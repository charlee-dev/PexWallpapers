package com.adwi.feature_settings.domain.privacy

data class PrivacyCategory(
    val name: String,
    val items: List<PrivacyItem>
)

data class PrivacyItem(
    val title: String,
    val description: String
)

val privacyCategoryList = listOf(
    PrivacyCategory(
        name = "COLLECTION OF YOUR INFORMATION",
        items = listOf(
            PrivacyItem(
                title = "Personal Data",
                description = "Demographic and other personally identifiable information (such as your name and email\n" +
                        "address) that you voluntarily give to us when choosing to participate in various activities related\n" +
                        "to the Application, such as chat, posting messages in comment sections or in our forums, liking\n" +
                        "posts, sending feedback, and responding to surveys. If you choose to share data about yourself\n" +
                        "via your profile, online chat, or other interactive areas of the Application, please be advised that\n" +
                        "all data you disclose in these areas is public and your data will be accessible to anyone who\n" +
                        "accesses the Application."
            ),
            PrivacyItem(
                title = "Derivative Data",
                description = "Information our servers automatically collect when you access the Application, such as your\n" +
                        "native actions that are integral to the Application, including liking, re-blogging, or replying to a\n" +
                        "post, as well as other interactions with the Application and other users via server log files. "
            ),
            PrivacyItem(
                title = "Financial Data : Not applicable for VDLNedcar apps.",
                description = "There is no payment necessary for this Application. Financial information, such as data related\n" +
                        "to your payment method is for this application Not Applicable."
            ),
            PrivacyItem(
                title = "Facebook Permissions : Not applicable for VDLNedcar apps",
                description = ""
            ),
            PrivacyItem(
                title = "Data from Social Networks : Not applicable for VDLNedcar apps.",
                description = "User information from social networking sites, such as [Apple’s Game Center, Facebook,\n" +
                        "Google+ Instagram, Pinterest, Twitter], including your name, your social network username,\n" +
                        "location, gender, birth date, email address, profile picture, and public data for contacts, if you\n" +
                        "connect your account to such social networks. This information may also include the contact\n" +
                        "information of anyone you invite to use and/or join the Application."
            ),
            PrivacyItem(
                title = "Geo-Location Information : Not applicable for VDLNedcar apps.",
                description = "We may request access or permission to and track location-based information from your mobile\n" +
                        "device, either continuously or while you are using the Application, to provide location-based\n" +
                        "services. If you wish to change our access or permissions, you may do so in your device’s\n" +
                        "settings."
            ),
        )

    ),
    PrivacyCategory(
        name = "DISCLOSURE OF YOUR INFORMATION",
        items = listOf(
            PrivacyItem(
                title = "By Law or to Protect Rights",
                description = "If we believe the release of information about you is necessary to respond to legal process, to\n" +
                        "investigate or remedy potential violations of our policies, or to protect the rights, property, and\n" +
                        "safety of others, we may share your information as permitted or required by any applicable law,\n" +
                        "rule, or regulation. This includes exchanging information with other entities for fraud protection\n" +
                        "and credit risk reduction."
            ),
            PrivacyItem(
                title = "Third-Party Service Providers : Not applicable for VDLNedcar apps.",
                description = "We may share your information with third parties that perform services for us or on our behalf,\n" +
                        "including payment processing, data analysis, email delivery, hosting services, customer service,\n" +
                        "and marketing assistance."
            ),
            PrivacyItem(
                title = "Marketing Communications : Not applicable for VDLNedcar apps.",
                description = "With your consent, or with an opportunity for you to withdraw consent, we may share your\n" +
                        "information with third parties for marketing purposes, as permitted by law."
            )
        )
    )
)