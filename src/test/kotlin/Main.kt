import java.io.StringWriter
import javax.xml.bind.JAXBContext
import javax.xml.bind.Marshaller
import javax.xml.bind.annotation.*


@XmlRootElement
data class BookStore(
    @XmlAttribute val address: String = "",
    @XmlElement var books: MutableList<Book> = mutableListOf()
)

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
data class Book(
    @XmlElement val title: String? = null,
    @XmlElement val author: String? = null,
    @XmlElement val year: Int? = null,
    @XmlElement val price: Float? = null
)

fun main() {
    val jaxbContext = JAXBContext.newInstance(BookStore::class.java)
    val marshaller = jaxbContext.createMarshaller()
    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true)
    val unmarshaller = jaxbContext.createUnmarshaller()

    val testBookStore = BookStore(
        address = "potato hill",
        books = mutableListOf(
            Book(title = "Potato Potter", author = "J K Potating", year = 2005, price = 4.99f),
            Book(title = "Potato Potter 2", author = "J K Potating", year = 2005, price = 6.99f),
            Book(title = "Potato Potter 3", author = "J K Potating")
        )
    )

    val stringWriter = StringWriter()
    marshaller.marshal(testBookStore, stringWriter)
    println(stringWriter.toString())

    val bookStoreFromXml = unmarshaller.unmarshal(testXml.reader()) as BookStore
    println(bookStoreFromXml)

    println(testBookStore == bookStoreFromXml)
}


val testXml = """
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<bookStore address="potato hill">
    <books>
        <title>Potato Potter</title>
        <author>J K Potating</author>
        <year>2005</year>
        <price>4.99</price>
    </books>
    <books>
        <title>Potato Potter 2</title>
        <author>J K Potating</author>
        <year>2005</year>
        <price>6.99</price>
    </books>
    <books>
        <title>Potato Potter 3</title>
        <author>J K Potating</author>
    </books>
</bookStore>
""".trimIndent()