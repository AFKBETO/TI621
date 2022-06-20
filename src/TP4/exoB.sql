# Exo i --
use tp4;

select DocumentName, Name as Category, Topic, Tag from Document
    join Category using(CategoryID)
    join Topic using(TopicID)
    join Possede using(DocumentID)
    join Tag using(TagID)
group by Category, Topic, Tag;

# Exo ii --
select count(*) as NbTopicTimes, Topic from Document
    join Topic using(TopicID)
group by Topic
order by NbTopicTimes desc
limit 1;

# Exo iii
select Tag, count(Tag) as NbOccurrenceTag from Possede
    join Tag using(TagID)
group by Tag
order by NbOccurrenceTag;

